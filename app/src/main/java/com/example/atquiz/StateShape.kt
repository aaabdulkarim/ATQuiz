package com.example.atquiz

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class StateShape(private val svgPath: String) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val path = PathParser().parsePathString(svgPath).toPath()
        return Outline.Generic(path)
    }
}


@Composable
fun SvgCanvas() {
    val svgPath = stringResource(id = R.string.lower_austria)
    Canvas(
        modifier = Modifier.size(200.dp).clickable { Log.e("NIEDERÖSTERREICH", "NIEDERÖSTERREICH") }
    ) {
        val path = PathParser().parsePathString(svgPath).toPath()
        drawPath(path = path, color = Color.Red)
    }
}