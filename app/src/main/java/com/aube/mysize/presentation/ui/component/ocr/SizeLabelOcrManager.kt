package com.aube.mysize.presentation.ui.component.ocr

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class SizeLabelOcrManager {
    private val koreanRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    private val englishRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun recognize(
        image: InputImage,
        onResult: (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        var koreanResult: List<String>? = null
        var englishResult: List<String>? = null

        val checkAndReturn = {
            if (koreanResult != null && englishResult != null) {
                val distinctKoreanLabel = koreanResult!!.distinct()
                val distinctEnglishLabel = englishResult!!.distinct()
                val finalResult = if (distinctKoreanLabel.size >= distinctEnglishLabel.size) distinctKoreanLabel else distinctEnglishLabel
                if (finalResult.isNotEmpty()) {
                    Log.d("OCR_DEBUG", "최종 선택된 라벨: $finalResult")
                    onResult(finalResult)
                } else {
                    onFailure("라벨을 인식하지 못했습니다.")
                }
            }
        }

        koreanRecognizer.process(image)
            .addOnSuccessListener { text ->
                koreanResult = text.text.lines().map { it.trim().uppercase() }.filter { it.isNotBlank() }
                Log.d("OCR_DEBUG", "Korean OCR 결과: $koreanResult")
                checkAndReturn()
            }
            .addOnFailureListener {
                Log.e("OCR_DEBUG", "Korean OCR 실패: ${it.message}")
                koreanResult = emptyList()
                checkAndReturn()
            }

        englishRecognizer.process(image)
            .addOnSuccessListener { text ->
                englishResult = text.text.lines().map { it.trim().uppercase() }.filter { it.isNotBlank() }
                Log.d("OCR_DEBUG", "English OCR 결과: $englishResult")
                checkAndReturn()
            }
            .addOnFailureListener {
                Log.e("OCR_DEBUG", "English OCR 실패: ${it.message}")
                englishResult = emptyList()
                checkAndReturn()
            }
    }
}
