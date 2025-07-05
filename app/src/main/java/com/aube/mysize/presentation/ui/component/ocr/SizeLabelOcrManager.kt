package com.aube.mysize.presentation.ui.component.ocr

import android.content.Context
import com.aube.mysize.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import timber.log.Timber

class SizeLabelOcrManager(
    private val context: Context,
) {
    private val koreanRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    private val englishRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun recognize(
        image: InputImage,
        onResult: (List<String>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        var koreanResult: List<String>? = null
        var englishResult: List<String>? = null

        val checkAndReturn = {
            if (koreanResult != null && englishResult != null) {
                val distinctKoreanLabel = koreanResult!!.distinct()
                val distinctEnglishLabel = englishResult!!.distinct()
                val finalResult = if (distinctKoreanLabel.size >= distinctEnglishLabel.size) distinctKoreanLabel else distinctEnglishLabel
                if (finalResult.isNotEmpty()) {
                    Timber.tag("OCR").d("Korean OCR: $koreanResult, English OCR: $englishResult → 최종: $finalResult")
                    onResult(finalResult)
                } else {
                    onFailure(context.getString(R.string.ocr_label_not_detected))
                }
            }
        }

        koreanRecognizer.process(image)
            .addOnSuccessListener { text ->
                koreanResult = text.text.lines().map { it.trim().uppercase() }.filter { it.isNotBlank() }
                Timber.tag("OCR").d("Korean OCR 결과: $koreanResult")
                checkAndReturn()
            }
            .addOnFailureListener {
                Timber.tag("OCR").e("Korean OCR 실패: ${it.message}")
                koreanResult = emptyList()
                checkAndReturn()
            }

        englishRecognizer.process(image)
            .addOnSuccessListener { text ->
                englishResult = text.text.lines().map { it.trim().uppercase() }.filter { it.isNotBlank() }
                Timber.tag("OCR").d("English OCR 결과: $englishResult")
                checkAndReturn()
            }
            .addOnFailureListener {
                Timber.tag("OCR").e("English OCR 실패: ${it.message}")
                englishResult = emptyList()
                checkAndReturn()
            }
    }
}
