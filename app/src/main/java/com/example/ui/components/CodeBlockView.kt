package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CodeComment
import com.example.ui.theme.CodeFunction
import com.example.ui.theme.CodeKeyword
import com.example.ui.theme.CodeNumber
import com.example.ui.theme.CodeOperator
import com.example.ui.theme.CodeSmallTextStyle
import com.example.ui.theme.CodeString
import com.example.ui.theme.CodeSurface
import com.example.ui.theme.CodeSurfaceHeader
import com.example.ui.theme.CodeTextStyle
import com.example.ui.theme.CodeType
import com.example.ui.theme.PythonEmerald
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate800
import kotlinx.coroutines.delay

@Composable
fun CodeBlockView(
    code: String,
    languageName: String,
    languageColor: Color,
    expectedOutput: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(2000)
            isCopied = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("code_block_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CodeSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Editor Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CodeSurfaceHeader)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Window dot controls + Language Pill
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF59E0B))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981))
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = languageColor.copy(alpha = 0.18f)
                    ) {
                        Text(
                            text = languageName.uppercase(),
                            style = CodeSmallTextStyle.copy(
                                fontWeight = FontWeight.Bold,
                                color = languageColor,
                                fontSize = 10.5.sp
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Copy Code Action
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedVisibility(
                        visible = isCopied,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = PythonEmerald.copy(alpha = 0.2f),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = PythonEmerald,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Copied!",
                                    style = CodeSmallTextStyle.copy(
                                        color = PythonEmerald,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("code", code)
                            clipboard.setPrimaryClip(clip)
                            isCopied = true
                        },
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("copy_code_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy code snippet",
                            tint = if (isCopied) PythonEmerald else Slate400,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Syntax Highlighted Code with Line Numbers
            val lines = remember(code) { code.lines() }
            val scrollState = rememberScrollState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp)
                    .horizontalScroll(scrollState)
            ) {
                // Line Numbers Column
                Column(modifier = Modifier.padding(end = 14.dp)) {
                    lines.indices.forEach { index ->
                        Text(
                            text = (index + 1).toString().padStart(2, ' '),
                            style = CodeTextStyle.copy(color = Slate600),
                            lineHeight = 22.sp
                        )
                    }
                }

                // Highlighting Code Block
                Column {
                    lines.forEach { line ->
                        Text(
                            text = highlightSyntax(line),
                            style = CodeTextStyle,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Expected Output Box (Terminal stdout)
            if (!expectedOutput.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF070B13))
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = null,
                                tint = PythonEmerald,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "EXPECTED OUTPUT",
                                style = CodeSmallTextStyle.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Slate400,
                                    fontSize = 10.5.sp
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Slate800.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = expectedOutput,
                                style = CodeSmallTextStyle.copy(
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Lightweight syntax highlighter applying token colors based on keywords,
 * strings, comments, numbers, types, and functions.
 */
fun highlightSyntax(line: String): AnnotatedString {
    return buildAnnotatedString {
        val trimmed = line.trimStart()
        if (trimmed.startsWith("//") || trimmed.startsWith("#")) {
            append(AnnotatedString(line, SpanStyle(color = CodeComment)))
            return@buildAnnotatedString
        }

        var i = 0
        while (i < line.length) {
            // Check for inline comment
            if ((line[i] == '/' && i + 1 < line.length && line[i + 1] == '/') || line[i] == '#') {
                val commentPart = line.substring(i)
                append(AnnotatedString(commentPart, SpanStyle(color = CodeComment)))
                break
            }

            // Check for string literals
            if (line[i] == '"' || line[i] == '\'') {
                val quote = line[i]
                var j = i + 1
                while (j < line.length && line[j] != quote) {
                    if (line[j] == '\\' && j + 1 < line.length) j++
                    j++
                }
                val end = if (j < line.length) j + 1 else line.length
                val strLiteral = line.substring(i, end)
                append(AnnotatedString(strLiteral, SpanStyle(color = CodeString)))
                i = end
                continue
            }

            // Check for numbers
            if (line[i].isDigit()) {
                var j = i
                while (j < line.length && (line[j].isDigit() || line[j] == '.' || line[j] == 'x' || line[j] == 'f')) {
                    j++
                }
                val numLiteral = line.substring(i, j)
                append(AnnotatedString(numLiteral, SpanStyle(color = CodeNumber)))
                i = j
                continue
            }

            // Check for words (identifiers / keywords / types)
            if (line[i].isLetter() || line[i] == '_') {
                var j = i
                while (j < line.length && (line[j].isLetterOrDigit() || line[j] == '_')) {
                    j++
                }
                val word = line.substring(i, j)
                val color = when (word) {
                    // Keywords (Magenta / Rose)
                    "def", "class", "public", "private", "protected", "static", "void", "return",
                    "if", "else", "elif", "match", "case", "for", "while", "do", "break", "continue",
                    "new", "include", "typedef", "struct", "record", "auto", "const", "import" -> CodeKeyword

                    // Types (Indigo Violet)
                    "int", "float", "double", "char", "bool", "boolean", "String", "var",
                    "std", "vector", "List", "Integer", "Resource", "Developer", "Person" -> CodeType

                    // Functions (Warm Amber)
                    "print", "println", "printf", "main", "malloc", "free", "make_unique", "size",
                    "length", "describe", "greet", "toList", "stream", "map" -> CodeFunction

                    // Constants
                    "True", "False", "true", "false", "None", "null", "NULL" -> CodeKeyword

                    else -> CodeOperator
                }
                append(AnnotatedString(word, SpanStyle(color = color)))
                i = j
                continue
            }

            // Operators / Punctuation
            append(AnnotatedString(line[i].toString(), SpanStyle(color = CodeOperator)))
            i++
        }
    }
}
