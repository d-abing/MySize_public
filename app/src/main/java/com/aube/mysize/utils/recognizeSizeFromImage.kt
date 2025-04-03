package com.aube.mysize.utils

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

fun recognizeSizeFromImage(
    image: InputImage,
    keyList: List<String>,
    onResult: (SizeExtractionResult) -> Unit
) {
    val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            val lines = visionText.text.lines()
                .map { it.trim() }
                .filter { it.isNotBlank() }

            Log.d("OCR_DEBUG", "전체 인식 텍스트:\n" + lines.joinToString("\n"))

            // ✅ 표 형태 판별
            val headerCount = lines.take(6).count { line ->
                keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
            }
            val isVertical = headerCount >= 3
            Log.d("OCR_DEBUG", "표 형태: ${if (isVertical) "vertical" else "horizontal"}")

            if (isVertical) {
                handleVerticalTable(lines, keyList, onResult)
            } else {
                handleHorizontalTable(lines, keyList, onResult)
            }
        }
        .addOnFailureListener {
            Log.e("OCR_DEBUG", "OCR 실패: ${it.message}", it)
            onResult(SizeExtractionResult.Failed)
        }
}

// ✅ 가로형 테이블 파싱
private fun handleHorizontalTable(
    lines: List<String>,
    keyList: List<String>,
    onResult: (SizeExtractionResult) -> Unit
) {
    val tableStartIndex = lines.indexOfFirst { line ->
        keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", ""), ignoreCase = true) }
    }.takeIf { it >= 0 } ?: 0

    val sizeLabels = lines.drop(1).take(tableStartIndex - 1)
    Log.d("OCR_DEBUG", "사이즈 라벨 목록: $sizeLabels")

    val tableLines = lines.drop(tableStartIndex)
    Log.d("OCR_DEBUG", "테이블 추정 라인: ${tableLines.joinToString(" | ")}")

    val columnMap = mutableMapOf<String, MutableList<String>>()
    var currentKey: String? = null
    var currentColumn = mutableListOf<String>()

    for (line in tableLines) {
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

    Log.d("OCR_DEBUG", "열 재구성 결과: $columnMap")

    val maxCount = columnMap.values.maxOfOrNull { it.size } ?: 0
    if (sizeLabels.size < maxCount) {
        Log.d("OCR_DEBUG", "⚠️ 사이즈 라벨 개수 부족: ${sizeLabels.size} < $maxCount")
        onResult(SizeExtractionResult.Incomplete)
        return
    }

    val result = mutableMapOf<String, Map<String, String>>()
    for (i in sizeLabels.indices) {
        val label = sizeLabels[i]
        val values = columnMap.mapNotNull { (key, list) ->
            list.getOrNull(i)?.let { key to it }
        }.toMap()
        result[label.uppercase()] = values
    }

    Log.d("OCR_DEBUG", "최종 추출: $result")
    onResult(SizeExtractionResult.Success(sizeLabels, result))
}

// ✅ 세로형 테이블 파싱
private fun handleVerticalTable(
    lines: List<String>,
    keyList: List<String>,
    onResult: (SizeExtractionResult) -> Unit
) {
    val headerIndices = lines.withIndex().filter { (_, line) ->
        keyList.any { key -> line.replace(" ", "").equals(key.replace(" ", ""), ignoreCase = true) }
    }.map { it.index }

    if (headerIndices.isEmpty()) {
        onResult(SizeExtractionResult.Failed)
        return
    }

    val headerStart = headerIndices.first()
    val headerEnd = headerIndices.last()
    val headers = lines.slice(headerStart..headerEnd)

    Log.d("OCR_DEBUG", "🔸 세로형 헤더: $headers")

    // 데이터 시작 위치: 숫자 or 유효한 라벨이 나오는 첫 줄
    val dataStartIndex = lines.withIndex().indexOfFirst { (index, line) ->
        index > headerEnd && (
                line.matches(Regex("^[0-9]+(\\.[0-9]+)?$")) || isValidLabel(line)
                )
    }

    val chunkSize = dataStartIndex + 1

    if (dataStartIndex == -1) {
        onResult(SizeExtractionResult.Failed)
        return
    }

    val dataLines = lines.drop(dataStartIndex)

    val sizeMap = mutableMapOf<String, Map<String, String>>()
    val sizeLabels = mutableListOf<String>()
    var chunkIndex = 0

    for (i in dataLines.indices step chunkSize) {
        val chunk: List<String>
        val labelCandidate = dataLines[i]

        Log.d("OCR_DEBUG", "라벨 후보: $labelCandidate")
        var map: Map<String, String>
        val label: String
        if (isValidLabel(labelCandidate)) {
            Log.d("OCR_DEBUG", "유효한 라벨 후보: $labelCandidate")
            chunk = dataLines.slice(i + 1..< i + chunkSize)
            map = headers.zip(chunk).toMap()
            label = labelCandidate.uppercase()
        } else if (i != 0 && isValidLabel(dataLines[i - 1])) {
            Log.d("OCR_DEBUG", "이전 라벨 사용: ${dataLines[i - 1]}")
            chunk = dataLines.slice(i..< i + chunkSize - 1)
            map = headers.zip(chunk).toMap()
            label = dataLines[i - 1].uppercase()
        } else {
            Log.d("OCR_DEBUG", "기본 라벨 사용: 사이즈${chunkIndex + 1}")
            chunk = dataLines.slice(i..< i + chunkSize - 1)
            map = headers.zip(chunk).toMap()
            label = "사이즈${++chunkIndex}"
        }
        Log.d("OCR_DEBUG", "추출된 라벨: $label, 추출된 값: $map")

        sizeLabels.add(label)
        sizeMap[label] = map
    }

    Log.d("OCR_DEBUG", "세로형 추출 라벨: $sizeLabels")
    Log.d("OCR_DEBUG", "세로형 최종 추출: $sizeMap")

    if (sizeLabels.size < sizeMap.size) {
        onResult(SizeExtractionResult.Incomplete)
    } else {
        onResult(SizeExtractionResult.Success(sizeLabels, sizeMap))
    }
}

private fun isValidLabel(label: String): Boolean {
    val trimmed = label.trim()
    val validLabels = listOf("XS", "S", "M", "L", "XL", "XXL", "FREE", "F", "90", "95", "100", "105", "110")
    return trimmed.uppercase() in validLabels
}
