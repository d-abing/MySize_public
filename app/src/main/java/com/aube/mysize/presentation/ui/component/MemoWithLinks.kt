package com.aube.mysize.presentation.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun MemoWithLinks(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
) {
    val context = LocalContext.current
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val annotatedText = remember(text) {
        buildAnnotatedString {
            val urlRegex = "(http[s]?://[^\\s]+)".toRegex()
            var lastIndex = 0

            urlRegex.findAll(text).forEach { result ->
                val start = result.range.first
                val end = result.range.last + 1

                append(text.substring(lastIndex, start))

                pushStringAnnotation(tag = "URL", annotation = result.value)
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(result.value)
                }
                pop()

                lastIndex = end
            }

            if (lastIndex < text.length) {
                append(text.substring(lastIndex))
            }
        }
    }

    Text(
        text = annotatedText,
        style = style,
        modifier = modifier
            .fillMaxWidth()
            .clickable { }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    layoutResult?.let {
                        val position = it.getOffsetForPosition(offset)
                        annotatedText.getStringAnnotations("URL", position, position)
                            .firstOrNull()?.let { annotation ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                context.startActivity(intent)
                            }
                    }
                }
            },
        onTextLayout = { layoutResult = it }
    )
}

