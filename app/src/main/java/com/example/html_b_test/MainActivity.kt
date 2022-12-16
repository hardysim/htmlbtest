package com.example.html_b_test

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.HtmlCompat
import com.example.html_b_test.ui.theme.HtmlbtestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Test()
        }
    }

    companion object {
        val HTML_TEXT = "Can <b>you</b> be bold?"
        val attentionStyle = SpanStyle(fontWeight = FontWeight.Bold)

        @Composable
        fun Test() {
            HtmlbtestTheme {
                val spanned = HTML_TEXT.toSpanned()
                val annotatedString = spanned.toAnnotatedString()

                Text(annotatedString)
            }
        }
    }
}

fun String.toSpanned(htmlCompatMode: Int = HtmlCompat.FROM_HTML_MODE_LEGACY) = HtmlCompat.fromHtml(this, htmlCompatMode)

@Composable
@ReadOnlyComposable
fun Spanned.toAnnotatedString(
    attentionStyle: SpanStyle = MainActivity.attentionStyle,
): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(attentionStyle, start, end)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    MainActivity.Test()
}
