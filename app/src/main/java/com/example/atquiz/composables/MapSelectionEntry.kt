package com.example.atquiz.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapSelectionEntry(short: String, long: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(20.dp)
    ) {
        Card {
            Column(
                modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {
                    onClick()
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = short, fontSize = 100.sp)
                Text(text = long)
            }
        }
    }
}