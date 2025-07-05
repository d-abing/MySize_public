package com.aube.mysize.presentation.ui.component

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun MemoWithLinks(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
) {
    val context = LocalContext.current
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val urlColor = MaterialTheme.colorScheme.primaryContainer

    val annotatedText = remember(text) {
        buildAnnotatedString {
            val urlRegex = Patterns.WEB_URL.toRegex()
            var lastIndex = 0

            urlRegex.findAll(text).forEach { result ->
                val start = result.range.first
                val end = result.range.last + 1

                append(text.substring(lastIndex, start))

                pushStringAnnotation(tag = "URL", annotation = result.value)
                withStyle(
                    style = SpanStyle(
                        color = urlColor,
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }
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

@Preview(showBackground = true)
@Composable
fun MemoWithLinksPreview() {
    MySizeTheme {
        MemoWithLinks(
            text = "이 앱은 https://mysize.app에서 확인할 수 있습니다.\n문의: http://help.mysize.app",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}