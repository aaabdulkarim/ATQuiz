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
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import com.example.atquiz.models.AUSTRIA_PARTS
import com.example.atquiz.models.MapPart
import com.example.atquiz.models.MapShape

const val MAX_ZOOM = 5f

@Composable
fun PartButton(path: String, isDisabled: Boolean ,name: String, onclick: (name: String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(MapShape(path))
    ) {
        Button(
            enabled = !isDisabled,
            onClick = {
                Log.d(name, name)
                onclick(name)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.fillMaxSize()
        ) {}
    }

}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FullMap(partList: List<MapPart>, buttonsDisabled: Boolean ,onclick: (name: String) -> Unit) {
    var scale by remember {
        mutableFloatStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

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
                for(part in partList) {
                    PartButton(path = part.svgPath, isDisabled = buttonsDisabled, name = part.name,
                        onclick = { name ->
                            onclick(name)
                        }
                    )
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp)) {
            Slider(
                value = (scale-1)/4,
                onValueChange = { scale = it*4+1 }
            )
        }
    }
}