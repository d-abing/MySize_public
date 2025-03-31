package com.aube.mysize.utils

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

fun recognizeSizeFromImage(
    image: InputImage,
    sizeLabel: String,
    keyList: List<String>,
    onResult: (Map<String, String>) -> Unit
) {
    val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            val lines = visionText.text.lines()
                .map { it.trim() }
                .filter { it.isNotBlank() }

            Log.d("OCR_DEBUG", "전체 인식 텍스트:\n" + lines.joinToString("\n"))

            // ✅ 1. 테이블 시작 인덱스 찾기
            val tableStartIndex = lines.indexOfFirst { line ->
                keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", "")) }
            }.takeIf { it >= 0 } ?: 0

            // ✅ 2. 사이즈 라벨은 그 이전 줄들
            val sizeLabels = lines.drop(1).take(tableStartIndex - 1)
            Log.d("OCR_DEBUG", "사이즈 라벨 목록: $sizeLabels")

            // ✅ 3. 테이블 라인 파싱
            val tableLines = lines.drop(tableStartIndex)
            Log.d("OCR_DEBUG", "테이블 추정 라인: ${tableLines.joinToString(" | ")}")

            val columns = mutableMapOf<String, MutableList<String>>()
            var currentKey: String? = null
            var currentColumn = mutableListOf<String>()

            for (line in tableLines) {
                val isKey = keyList.any { key -> line.replace(" ", "").contains(key.replace(" ", "")) }
                if (isKey) {
                    if (currentKey != null && currentColumn.isNotEmpty()) {
                        columns[currentKey] = currentColumn.toMutableList()
                    }
                    currentKey = keyList.first { key -> line.replace(" ", "").contains(key.replace(" ", "")) }
                    currentColumn = mutableListOf()
                } else {
                    currentColumn.add(line)
                }
            }
            currentKey?.let { columns[it] = currentColumn }

            Log.d("OCR_DEBUG", "열 재구성 결과: $columns")

            // ✅ 4. 사이즈 라벨 인덱스 찾기
            val normalizedLabel = sizeLabel.trim().lowercase()
            val rowIndex = sizeLabels.indexOfFirst {
                it.trim().lowercase().replace(" ", "") == normalizedLabel
            }

            Log.d("OCR_DEBUG", "사이즈 라벨 '$sizeLabel' → 행 인덱스: $rowIndex")

            // ✅ 5. 추출
            val result = if (rowIndex != -1) {
                columns.mapNotNull { (key, values) ->
                    if (values.size > rowIndex) {
                        key to values[rowIndex]
                    } else null
                }.toMap()
            } else emptyMap()

            Log.d("OCR_DEBUG", "최종 추출: $result")
            onResult(result)
        }
        .addOnFailureListener {
            Log.e("OCR_DEBUG", "OCR 실패: ${it.message}", it)
            onResult(emptyMap())
        }
}
