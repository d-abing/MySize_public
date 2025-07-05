
import androidx.core.text.isDigitsOnly
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

    fun recognize(image: InputImage, onResult: (SizeExtractionResult) -> Unit) {
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processText(visionText, onResult)
            }
            .addOnFailureListener { e ->
                Timber.tag("OCR").e(e, "OCR 실패: ${e.message}")
                onResult(SizeExtractionResult.OcrFailed(e.message))
            }
    }

    private fun processText(visionText: Text, onResult: (SizeExtractionResult) -> Unit) {
        val lines = visionText.text.lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

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
        return if (headerCount >= 3) TableType.Vertical else if (headerCount >= 1) TableType.Horizontal else TableType.Unknown
    }

    private fun handleHorizontalTable(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit,
    ) {
        // 1. 데이터 시작 인덱스 (헤더 찾기)
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

        // 2. 열별로 데이터 재구성
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
            } else if (line.isDigitsOnly()){
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


    private fun handleVerticalTable(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit,
    ) {
        // 1. 헤더 인덱스 찾기
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

        // 2. 데이터 시작 인덱스 찾기 (사이즈 라벨 or 숫자)
        val dataStartIndex = lines.indexOfFirst { it.trim().uppercase() in sizeLabels }

        val chunkSize = dataStartIndex - headerStart
        val dataLines = lines.drop(dataStartIndex)
        Timber.tag("OCR").d("📋 세로형 데이터 라인: ${dataLines.joinToString(" | ")}")

        val sizeMap = mutableMapOf<String, Map<String, String>>()
        var currentSizeLabel: String? = null
        var currentSizeLabelIndex = 0
        var currentChunk = mutableListOf<String>()

        // 3. 데이터 파싱
        for (line in dataLines) {
            val trimmedUpperLabel = line.trim().uppercase()
            if (isValidLabel(trimmedUpperLabel)) {
                // 새 사이즈 라벨 시작
                currentSizeLabel = trimmedUpperLabel
                currentSizeLabelIndex++
                Timber.tag("OCR").d("새 사이즈 라벨: $currentSizeLabel")
            } else {
                currentChunk.add(line)

                if (currentChunk.size == chunkSize) {
                    if (currentSizeLabel == null) {
                        currentSizeLabel = sizeLabels[currentSizeLabelIndex++]
                    }
                    sizeMap[currentSizeLabel] = headers.zip(currentChunk).toMap()
                    Timber.tag("OCR").d("추출된 사이즈: $currentSizeLabel -> $currentChunk")

                    // 초기화
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
