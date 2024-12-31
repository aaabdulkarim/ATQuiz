package com.example.atquiz.composables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import com.example.atquiz.StateShape

@Composable
fun AustriaMap(path: String, name: String) {
    Box {
        Button(
            modifier = Modifier
                .size(800.dp)
//                .clip(StateShape(path)),
                    ,
            onClick = { Log.d(name, name) },
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(1.dp, Color.Red)
        ) {}
    }
}