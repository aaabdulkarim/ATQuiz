package com.example.atquiz.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atquiz.R

const val MAX_ZOOM = 5f

@Composable
fun AustriaMap(path: String, name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(StateShape(path))
    ) {
        Button(
            onClick = { Log.d(name, name) },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.fillMaxSize()
        ) {}
    }

}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FullMap() {
    var scale by remember {
        mutableStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    var sliderPosition by remember { mutableFloatStateOf(scale/5) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().clipToBounds()
    ) {
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->

            scale = (scale * zoomChange).coerceIn(1f, MAX_ZOOM)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
            )
        }
        Box(modifier = Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                AustriaMap(stringResource(R.string.lower_austria), "Lower Austria")
                AustriaMap(stringResource(R.string.vienna), "Vienna")
                AustriaMap(stringResource(R.string.upper_austria), "Upper Austria")
                AustriaMap(stringResource(R.string.burgenland), "Burgenland")
                AustriaMap(stringResource(R.string.carinthia), "Carinthia")
                AustriaMap(stringResource(R.string.styria), "Styria")
                AustriaMap(stringResource(R.string.tyrol), "Tyrol")
                AustriaMap(stringResource(R.string.salzburg), "Salzburg")
                AustriaMap(stringResource(R.string.vorarlberg), "Vorarlberg")
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp)) {
            Text("${(scale)}")
            Slider(
                value = (scale-1)/4,
                onValueChange = { scale = it*4+1 }
            )
        }
    }
}