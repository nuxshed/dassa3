package com.dass.ims.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun imsicon(name: String, strokewidth: Float = 1.75f, vararg pathdata: String): ImageVector {
    return ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        pathdata.forEach { d ->
            path(
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = strokewidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            ) {
                parsesvgpath(d)
            }
        }
    }.build()
}

private fun androidx.compose.ui.graphics.vector.PathBuilder.parsesvgpath(d: String) {
    val tokens = tokenize(d)
    var i = 0

    fun floats(): List<Float> {
        if (i >= tokens.size || tokens[i].first != 'N') return emptyList()
        val result = mutableListOf<Float>()
        while (i < tokens.size && tokens[i].first == 'N') {
            result.add(tokens[i].second)
            i++
        }
        return result
    }

    while (i < tokens.size) {
        val (type, _) = tokens[i]
        if (type == 'N') { i++; continue }
        val cmd = type
        i++
        val n = floats()
        when (cmd) {
            'M' -> { if (n.size >= 2) { moveTo(n[0], n[1]); var j = 2; while (j + 1 < n.size) { lineTo(n[j], n[j + 1]); j += 2 } } }
            'm' -> { if (n.size >= 2) { moveToRelative(n[0], n[1]); var j = 2; while (j + 1 < n.size) { lineToRelative(n[j], n[j + 1]); j += 2 } } }
            'L' -> { var j = 0; while (j + 1 < n.size) { lineTo(n[j], n[j + 1]); j += 2 } }
            'l' -> { var j = 0; while (j + 1 < n.size) { lineToRelative(n[j], n[j + 1]); j += 2 } }
            'H' -> n.forEach { horizontalLineTo(it) }
            'h' -> n.forEach { horizontalLineToRelative(it) }
            'V' -> n.forEach { verticalLineTo(it) }
            'v' -> n.forEach { verticalLineToRelative(it) }
            'C' -> { var j = 0; while (j + 5 < n.size) { curveTo(n[j], n[j + 1], n[j + 2], n[j + 3], n[j + 4], n[j + 5]); j += 6 } }
            'c' -> { var j = 0; while (j + 5 < n.size) { curveToRelative(n[j], n[j + 1], n[j + 2], n[j + 3], n[j + 4], n[j + 5]); j += 6 } }
            'S' -> { var j = 0; while (j + 3 < n.size) { reflectiveCurveTo(n[j], n[j + 1], n[j + 2], n[j + 3]); j += 4 } }
            's' -> { var j = 0; while (j + 3 < n.size) { reflectiveCurveToRelative(n[j], n[j + 1], n[j + 2], n[j + 3]); j += 4 } }
            'Q' -> { var j = 0; while (j + 3 < n.size) { quadTo(n[j], n[j + 1], n[j + 2], n[j + 3]); j += 4 } }
            'q' -> { var j = 0; while (j + 3 < n.size) { quadToRelative(n[j], n[j + 1], n[j + 2], n[j + 3]); j += 4 } }
            'A' -> { var j = 0; while (j + 6 < n.size) { arcTo(n[j], n[j + 1], n[j + 2], n[j + 3] != 0f, n[j + 4] != 0f, n[j + 5], n[j + 6]); j += 7 } }
            'a' -> { var j = 0; while (j + 6 < n.size) { arcToRelative(n[j], n[j + 1], n[j + 2], n[j + 3] != 0f, n[j + 4] != 0f, n[j + 5], n[j + 6]); j += 7 } }
            'Z', 'z' -> close()
        }
    }
}

/// tokenize svg path string into command chars and numbers
private fun tokenize(d: String): List<Pair<Char, Float>> {
    val result = mutableListOf<Pair<Char, Float>>()
    val buf = StringBuilder()
    fun flush() {
        if (buf.isNotEmpty()) {
            val s = buf.toString().trim()
            if (s.isNotEmpty()) {
                s.toFloatOrNull()?.let { result.add('N' to it) }
            }
            buf.clear()
        }
    }
    for ((idx, c) in d.withIndex()) {
        when {
            c.isLetter() && c != 'e' && c != 'E' -> {
                flush()
                result.add(c to 0f)
            }
            c == ',' || c == ' ' -> flush()
            c == '-' && buf.isNotEmpty() && buf.last() != 'e' && buf.last() != 'E' -> {
                flush()
                buf.append(c)
            }
            c == '.' && buf.contains('.') -> {
                flush()
                buf.append(c)
            }
            else -> buf.append(c)
        }
    }
    flush()
    return result
}

object ImsIcons {
    val search = imsicon("search",
        pathdata = arrayOf(
            "M21 21l-4.3-4.3",
            "M10.5 18a7.5 7.5 0 1 1 0-15 7.5 7.5 0 0 1 0 15z",
        ))
    val x = imsicon("x", pathdata = arrayOf("M18 6L6 18M6 6l12 12"))
    val chevdown = imsicon("chevdown", pathdata = arrayOf("M6 9l6 6 6-6"))
    val chevright = imsicon("chevright", pathdata = arrayOf("M9 6l6 6-6 6"))
    val chevleft = imsicon("chevleft", pathdata = arrayOf("M15 6l-6 6 6 6"))
    val plus = imsicon("plus", pathdata = arrayOf("M12 5v14M5 12h14"))
    val arrow = imsicon("arrow", pathdata = arrayOf("M5 12h14M13 5l7 7-7 7"))
    val filter = imsicon("filter", pathdata = arrayOf("M3 6h18M6 12h12M10 18h4"))
    val more = imsicon("more", pathdata = arrayOf(
        "M12 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z",
        "M19 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z",
        "M5 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z",
    ))
    val check = imsicon("check", pathdata = arrayOf("M20 6L9 17l-5-5"))
    val pencil = imsicon("pencil", pathdata = arrayOf("M17 3a2.828 2.828 0 0 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"))
    val trash = imsicon("trash", pathdata = arrayOf(
        "M3 6h18",
        "M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2",
        "M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6",
    ))
    val msg = imsicon("msg", pathdata = arrayOf("M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"))
    val cal = imsicon("cal", pathdata = arrayOf(
        "M3 9h18",
        "M5 5h14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7a2 2 0 0 1 2-2z",
        "M8 3v4M16 3v4",
    ))
    val users = imsicon("users", pathdata = arrayOf(
        "M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2",
        "M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z",
        "M22 21v-2a4 4 0 0 0-3-3.87",
        "M16 3.13a4 4 0 0 1 0 7.75",
    ))
    val clip = imsicon("clip", pathdata = arrayOf(
        "M9 11h6M9 15h6",
        "M9 2h6a2 2 0 0 1 2 2v16a2 2 0 0 1-2 2H9a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2z",
        "M11 4h2",
    ))
    val file = imsicon("file", pathdata = arrayOf(
        "M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z",
        "M14 2v6h6",
    ))
    val bank = imsicon("bank", pathdata = arrayOf(
        "M3 10h18M5 6l7-4 7 4M4 10v8M8 10v8M16 10v8M20 10v8M2 21h20",
    ))
    val paper = imsicon("paper", pathdata = arrayOf(
        "M4 22h16a2 2 0 0 0 2-2V8l-6-6H6a2 2 0 0 0-2 2v4",
        "M14 2v6h6M18 14v8M14 18h8",
    ))
    val brief = imsicon("brief", pathdata = arrayOf(
        "M16 20V4a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16",
        "M2 13h20",
        "M4 7h16a2 2 0 0 1 2 2v9a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2z",
    ))
    val usercog = imsicon("usercog", pathdata = arrayOf(
        "M10 15H6a4 4 0 0 0-4 4v2",
        "M14 3a4 4 0 1 1 0 8 4 4 0 0 1 0-8z",
        "M19 16l-2 3M19 22l-2-3M22 19l-3-2M22 19l-3 2",
        "M19 16a3 3 0 1 0 0 6 3 3 0 0 0 0-6z",
    ))
    val userplus = imsicon("userplus", pathdata = arrayOf(
        "M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2",
        "M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z",
        "M20 8v6M17 11h6",
    ))
    val enter = imsicon("enter", pathdata = arrayOf(
        "M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4",
        "M10 17l5-5-5-5M15 12H3",
    ))
    val grip = imsicon("grip", pathdata = arrayOf(
        "M9 5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
        "M9 12a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
        "M9 19a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
        "M17 5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
        "M17 12a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
        "M17 19a1 1 0 1 1-2 0 1 1 0 0 1 2 0z",
    ))
    val mappin = imsicon("mappin", pathdata = arrayOf(
        "M20 10c0 7-8 12-8 12s-8-5-8-12a8 8 0 0 1 16 0z",
        "M12 13a3 3 0 1 0 0-6 3 3 0 0 0 0 6z",
    ))
    val user = imsicon("user", pathdata = arrayOf(
        "M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2",
        "M12 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z",
    ))
    val alert = imsicon("alert", pathdata = arrayOf(
        "M12 9v4M12 17h.01",
        "M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z",
    ))
    val clock = imsicon("clock", pathdata = arrayOf(
        "M12 22a10 10 0 1 0 0-20 10 10 0 0 0 0 20z",
        "M12 6v6l4 2",
    ))
    val book = imsicon("book", pathdata = arrayOf(
        "M4 19.5A2.5 2.5 0 0 1 6.5 17H20",
        "M4 19.5A2.5 2.5 0 0 0 6.5 22H20V2H6.5A2.5 2.5 0 0 0 4 4.5v15z",
    ))

    // bottom nav icons
    val navhome = imsicon("navhome", pathdata = arrayOf("M3 12l9-9 9 9M5 10v10h14V10"))
    val navsearch = search
    val navschedule = cal
    val navpeople = users
    val navme = user
}

@Composable
fun ImsIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = ImsTheme.colors.text,
    size: Dp = 18.dp,
    contentdescription: String? = null,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentdescription,
        modifier = modifier.size(size),
        tint = tint,
    )
}
