package com.example.atquiz.models

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.PathParser

class MapShape(private val svgPath: String) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val path = PathParser().parsePathString(svgPath).toPath()
        return Outline.Generic(path)
    }
}