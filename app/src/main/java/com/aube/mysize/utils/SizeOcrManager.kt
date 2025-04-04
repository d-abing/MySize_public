package com.aube.mysize.utils

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

class SizeOcrManager(
    private val keyList: List<String>,
    private val keyMapping: (String) -> String
) {
    private val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    fun recognize(image: InputImage, onResult: (SizeExtractionResult) -> Unit) {
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processText(visionText, onResult)
            }
            .addOnFailureListener { e ->
                Log.e("OCR_DEBUG", "OCR 실패: ${e.message}", e)
                onResult(SizeExtractionResult.OcrFailed(e.message))
            }
    }

    private fun processText(visionText: Text, onResult: (SizeExtractionResult) -> Unit) {
        val lines = visionText.text.lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

        Log.d("OCR_DEBUG", "전체 인식 텍스트:\n${lines.joinToString("\n")}")

        val tableType = detectTableType(lines)
        Log.d("OCR_DEBUG", "표 형태: $tableType")

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
        onResult: (SizeExtractionResult) -> Unit
    ) {
        // 1. 데이터 시작 인덱스 (헤더 찾기)
        val dataStartIndex = lines.indexOfFirst { line ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }

        if (dataStartIndex == -1) {
            Log.d("OCR_DEBUG", "❌ 가로형: 헤더 못찾음")
            onResult(SizeExtractionResult.NoHeaderFound)
            return
        }

        var sizeLabels = lines.take(dataStartIndex)
        if (sizeLabels.isEmpty()) {
            Log.d("OCR_DEBUG", "❌ 가로형: 사이즈 라벨 없음")
            onResult(SizeExtractionResult.NoSizeLabelFound)
            return
        }

        val dataLines = lines.drop(dataStartIndex)
        Log.d("OCR_DEBUG", "📋 데이터 라인: ${dataLines.joinToString(" | ")}")

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
            } else {
                currentColumn.add(line)
            }
        }
        currentKey?.let { columnMap[it] = currentColumn }

        Log.d("OCR_DEBUG", "🧩 열 재구성 결과: $columnMap")

        // 3. 사이즈별로 정리
        val maxSizeCount = columnMap.values.maxOfOrNull { it.size } ?: 0
        if (sizeLabels.size == maxSizeCount + 1) {
            // 첫줄 단위 같은 것 제거
            Log.d("OCR_DEBUG", "⚠️ 라벨 1개 초과, 첫줄 버림")
            sizeLabels = sizeLabels.drop(1)
        }
        if (sizeLabels.size != maxSizeCount) {
            Log.d("OCR_DEBUG", "❌ 가로형: 라벨 수 != 데이터 수")
            onResult(SizeExtractionResult.Incomplete)
            return
        }

        val result = mutableMapOf<String, Map<String, String>>()

        for (i in sizeLabels.indices) {
            val label = sizeLabels[i]
            val values = columnMap.mapNotNull { (key, list) ->
                list.getOrNull(i)?.let { keyMapping(key) to it }
            }.toMap()
            result[label.uppercase()] = values
        }

        Log.d("OCR_DEBUG", "✅ 최종 결과: $result")
        onResult(SizeExtractionResult.Success(sizeLabels, result))
    }


    private fun handleVerticalTable(
        lines: List<String>,
        onResult: (SizeExtractionResult) -> Unit
    ) {
        // 1. 헤더 인덱스 찾기
        val headerIndices = lines.withIndex().filter { (_, line) ->
            keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
        }.map { it.index }

        if (headerIndices.isEmpty()) {
            Log.d("OCR_DEBUG", "❌ 세로형: 헤더 못찾음")
            onResult(SizeExtractionResult.NoHeaderFound)
            return
        }

        val headerStart = headerIndices.first()
        val headerEnd = headerIndices.last()
        val headers = lines.slice(headerStart..headerEnd).map { keyMapping(it) }

        Log.d("OCR_DEBUG", "📋 세로형 헤더: $headers")

        // 2. 데이터 시작 인덱스 찾기 (사이즈 라벨 or 숫자)
        val dataStartIndex = lines.withIndex().indexOfFirst { (index, line) ->
            index > headerEnd && (isValidLabel(line) || line.matches(Regex("^[0-9]+(\\.[0-9]+)?$")))
        }

        if (dataStartIndex == -1) {
            Log.d("OCR_DEBUG", "❌ 세로형: 데이터 시작 라인 못찾음")
            onResult(SizeExtractionResult.NoSizeLabelFound)
            return
        }

        val chunkSize = dataStartIndex - headerStart
        val dataLines = lines.drop(dataStartIndex)
        Log.d("OCR_DEBUG", "📋 세로형 데이터 라인: ${dataLines.joinToString(" | ")}")

        val sizeMap = mutableMapOf<String, Map<String, String>>()
        val sizeLabels = mutableListOf<String>()
        var currentSizeLabel: String? = null
        var currentChunk = mutableListOf<String>()
        var chunkIndex = 0

        // 3. 데이터 파싱
        for (line in dataLines) {
            if (isValidLabel(line.trim())) {
                // 새 사이즈 라벨 시작
                currentSizeLabel = line.trim().uppercase()
                sizeLabels.add(currentSizeLabel)
            } else {
                currentChunk.add(line)

                if (currentChunk.size == chunkSize) {
                    if (currentSizeLabel == null) {
                        // 사이즈라벨이 없는 경우, 임시 라벨 부여
                        chunkIndex++
                        currentSizeLabel = "알 수 없는 사이즈$chunkIndex"
                        sizeLabels.add(currentSizeLabel)
                    }
                    sizeMap[currentSizeLabel] = headers.zip(currentChunk).toMap()

                    // 초기화
                    currentSizeLabel = null
                    currentChunk = mutableListOf()
                }
            }
        }

        Log.d("OCR_DEBUG", "🔖 세로형 추출된 라벨: $sizeLabels")
        Log.d("OCR_DEBUG", "✅ 세로형 최종 결과: $sizeMap")

        if (sizeLabels.size != sizeMap.size) {
            Log.d("OCR_DEBUG", "❌ 세로형: 추출 실패")
            onResult(SizeExtractionResult.Incomplete)
        } else {
            onResult(SizeExtractionResult.Success(sizeLabels, sizeMap))
        }
    }

    private fun isValidLabel(label: String): Boolean {
        val trimmed = label.trim()
        val validLabels = listOf(
            "XS", "S", "M", "L", "XL", "XXL", "XXXL", "XXXXL",
            "FREE", "F", "ONE SIZE", "OS",
            "SMALL", "MEDIUM", "LARGE", "X-LARGE", "XX-LARGE", "XXX-LARGE",
            "85", "90", "95", "100", "105", "110", "115", "120", "125", "130",
            "S/M", "M/L", "L/XL"
        )
        return trimmed.uppercase() in validLabels
    }
}

private enum class TableType { Vertical, Horizontal, Unknown }
