import android.os.Handler
import android.os.Looper
import com.aube.mysize.utils.isNumeric
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import timber.log.Timber

class SizeOcrManager(
    private val keyList: List<String>,
    private val keyMapping: (String) -> String,
    private var sizeLabels: List<String>,
) {
    private val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    fun recognizeWithRetry(
        image: InputImage,
        retryCount: Int = 3,
        delayMillis: Long = 2000,
        onResult: (SizeExtractionResult) -> Unit
    ) {
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processText(visionText, onResult)
            }
            .addOnFailureListener { e ->
                val isModelNotReady = e.message?.contains("Waiting for the text optional module") == true
                if (isModelNotReady && retryCount > 0) {
                    Timber.tag("OCR").w("모델 준비 중... ${retryCount}회 남음. 재시도 예정.")
                    Handler(Looper.getMainLooper()).postDelayed({
                        recognizeWithRetry(image, retryCount - 1, delayMillis, onResult)
                    }, delayMillis)
                } else {
                    Timber.tag("OCR").e(e, "OCR 실패: ${e.message}")
                    onResult(SizeExtractionResult.OcrFailed(e.message))
                }
            }
    }

    private fun processText(visionText: Text, onResult: (SizeExtractionResult) -> Unit) {
        val lines = visionText.text.lines().map { it.trim() }.filter { it.isNotBlank() }
        Timber.tag("OCR").d("전체 인식 텍스트:\n${lines.joinToString(" | ")}")

        val tableType = detectTableType(lines)
        Timber.tag("OCR").d("표 형태: $tableType")

        when (tableType) {
            TableType.Vertical -> handleVerticalTable(lines, onResult)
            TableType.Horizontal -> handleHorizontalTable(lines, onResult)
            TableType.Unknown -> onResult(SizeExtractionResult.NoHeaderFound)
        }
    }

    private fun detectTableType(lines: List<String>): TableType {
        val headerCount = lines.take(6).count { line ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }
        return when {
            headerCount >= 3 -> TableType.Vertical
            else -> TableType.Horizontal
        }
    }

    private fun handleHorizontalTable(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit,
    ) {
        val isMusinsa = lines.any { it.contains("사이즈를 직접 입력해주세요") }

        if (isMusinsa) {
            Timber.tag("OCR").d("🧬 무신사 전용 처리 시작")
            handleHorizontalTableForMusinsa(lines, onResult)
            return
        }

        val dataStartIndex = lines.indexOfFirst { line ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }

        if (dataStartIndex == -1) {
            Timber.tag("OCR").d("❌ 가로형: 헤더 못찾음")
            onResult(SizeExtractionResult.NoHeaderFound)
            return
        }

        val dataLines = lines.drop(dataStartIndex)
        Timber.tag("OCR").d("📋 데이터 라인: ${dataLines.joinToString(" | ")}")

        val columnMap = mutableMapOf<String, MutableList<String>>()
        var currentKey: String? = null
        var currentColumn = mutableListOf<String>()

        for (line in dataLines) {
            val isKey = keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
            if (isKey) {
                if (currentKey != null && currentColumn.isNotEmpty()) {
                    columnMap[currentKey] = currentColumn.toMutableList()
                }
                currentKey = keyList.first { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
                currentColumn = mutableListOf()
            } else if (line.isNumeric()) {
                currentColumn.add(line)
            }
        }
        currentKey?.let { columnMap[it] = currentColumn }

        Timber.tag("OCR").d("🧩 열 재구성 결과: $columnMap")

        if (columnMap.values.map { it.size }.distinct().size > 1) {
            Timber.tag("OCR").d("❌ 가로형: 불충분한 결과")
            onResult(SizeExtractionResult.Incomplete)
            return
        }

        val result = mutableMapOf<String, Map<String, String>>()

        for (i in sizeLabels.indices) {
            val label = sizeLabels[i]
            val values = columnMap.mapNotNull { (key, list) ->
                list.getOrNull(i)?.let { keyMapping(key) to it }
            }.toMap()
            if (label.matches(Regex("^[A-Z0-9/_\\- \\[\\]()]{1,10}$"))) {
                result[label.uppercase()] = values
            }
        }

        Timber.tag("OCR").d("✅ 최종 결과: $result")
        onResult(SizeExtractionResult.Success(sizeLabels, result))
    }

    private fun handleHorizontalTableForMusinsa(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit
    ) {
        val filteredLines = lines.filterNot {
            it.contains("내사이즈") || it.contains("내 사이즈") || it.contains("사이즈를 직접 입력해주세요")
        }

        val filteredLabels = sizeLabels.filterNot {
            it.contains("내사이즈") || it.contains("내 사이즈")
        }


        Timber.tag("OCR").d("📋 무신사 필터링된 라인: ${filteredLines.joinToString(" | ")}")

        val dataStartIndex = filteredLines.indexOfFirst { line ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }

        if (dataStartIndex == -1) {
            Timber.tag("OCR").d("❌ 무신사: 헤더 못찾음")
            onResult(SizeExtractionResult.NoHeaderFound)
            return
        }

        val dataLines = filteredLines.drop(dataStartIndex)
        val columnMap = mutableMapOf<String, MutableList<String>>()

        var currentKey: String? = null
        var currentColumn = mutableListOf<String>()

        for (line in dataLines) {
            val isKey = keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
            if (isKey) {
                if (currentKey != null && currentColumn.isNotEmpty()) {
                    columnMap[currentKey] = currentColumn.toMutableList()
                }
                currentKey = keyList.first { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
                currentColumn = mutableListOf()
            } else if (line.isNumeric()) {
                currentColumn.add(line)
            }
        }
        currentKey?.let { columnMap[it] = currentColumn }

        Timber.tag("OCR").d("🧩 무신사 열 재구성 전: $columnMap")

        // 보정 처리
        adjustMussinsaColumnMapIfNeeded(columnMap, filteredLabels)

        Timber.tag("OCR").d("🧩 무신사 열 재구성 결과: $columnMap")

        // 최종 매핑
        val result = mutableMapOf<String, Map<String, String>>()

        for (i in filteredLabels.indices) {
            val label = filteredLabels[i]
            val values = columnMap.mapNotNull { (key, list) ->
                list.getOrNull(i)?.let { keyMapping(key) to it }
            }.toMap()
            result[label.uppercase()] = values
        }

        Timber.tag("OCR").d("✅ 무신사 최종 결과: $result")
        onResult(SizeExtractionResult.Success(filteredLabels, result))
    }

    private fun handleVerticalTable(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit,
    ) {
        val headerIndices = lines.withIndex().filter { (_, line) ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }.map { it.index }

        if (headerIndices.isEmpty()) {
            Timber.tag("OCR").d("❌ 세로형: 헤더 못찾음")
            onResult(SizeExtractionResult.NoHeaderFound)
            return
        }

        val headerStart = headerIndices.first()
        val headerEnd = headerIndices.last()
        val headers = lines.slice(headerStart..headerEnd).map { keyMapping(it) }

        Timber.tag("OCR").d("📋 세로형 헤더: $headers")

        val dataStartIndex = lines.indexOfFirst { it.trim().uppercase() in sizeLabels }
        val chunkSize = dataStartIndex - headerStart
        val dataLines = lines.drop(dataStartIndex)
        Timber.tag("OCR").d("📋 세로형 데이터 라인: ${dataLines.joinToString(" | ")}")

        val sizeMap = mutableMapOf<String, Map<String, String>>()
        var currentSizeLabel: String? = null
        var currentSizeLabelIndex = 0
        var currentChunk = mutableListOf<String>()

        for (line in dataLines) {
            val trimmedUpperLabel = line.trim().uppercase()
            if (isValidLabel(trimmedUpperLabel)) {
                currentSizeLabel = trimmedUpperLabel
                currentSizeLabelIndex++
                Timber.tag("OCR").d("새 사이즈 라벨: $currentSizeLabel")
            } else {
                currentChunk.add(line)

                if (currentChunk.size == chunkSize) {
                    if (currentSizeLabel == null) {
                        currentSizeLabel = sizeLabels.getOrNull(currentSizeLabelIndex++) ?: "UNKNOWN"
                    }
                    sizeMap[currentSizeLabel] = headers.zip(currentChunk).toMap()
                    Timber.tag("OCR").d("추출된 사이즈: $currentSizeLabel -> $currentChunk")
                    currentSizeLabel = null
                    currentChunk = mutableListOf()
                }
            }
        }

        Timber.tag("OCR").d("✅ 세로형 최종 결과: $sizeMap")

        if (sizeLabels.size != sizeMap.size) {
            Timber.tag("OCR").d("❌ 세로형: 추출 실패")
            onResult(SizeExtractionResult.Incomplete)
        } else {
            onResult(SizeExtractionResult.Success(sizeLabels, sizeMap))
        }
    }

    private fun isValidLabel(label: String): Boolean = label in sizeLabels


    private fun adjustMussinsaColumnMapIfNeeded(
        columnMap: MutableMap<String, MutableList<String>>,
        sizeLabels: List<String>
    ) {
        val expectedSizeCount = sizeLabels.size
        val underfilled = columnMap.filterValues { it.size < expectedSizeCount }
        val overfilled = columnMap.filterValues { it.size > expectedSizeCount }

        Timber.tag("OCR").d("⛏ underfilled: $underfilled")
        Timber.tag("OCR").d("⛏ overfilled: $overfilled")

        if (underfilled.isEmpty() || overfilled.isEmpty()) return

        underfilled.forEach { (shortKey, shortList) ->
            val diff = expectedSizeCount - shortList.size
            val donorEntry = overfilled.entries.firstOrNull { it.value.size >= expectedSizeCount + diff }
            if (donorEntry != null) {
                val donorKey = donorEntry.key
                val donorList = donorEntry.value
                val transferValues = donorList.take(diff)
                donorList.subList(0, diff).clear()
                shortList.addAll(transferValues)
                Timber.tag("OCR").d("🔧 '$shortKey'에 '${donorKey}'에서 $transferValues 이동 보정")
            }
        }
    }

}

private enum class TableType { Vertical, Horizontal, Unknown }

sealed class SizeExtractionResult {
    data class Success(
        val sizeLabels: List<String>,
        val sizeMap: Map<String, Map<String, String>>,
    ) : SizeExtractionResult()

    object Incomplete : SizeExtractionResult()
    object NoHeaderFound : SizeExtractionResult()
    data class OcrFailed(val message: String?) : SizeExtractionResult()
}
