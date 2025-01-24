package com.example.atquiz.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.atquiz.composables.MapSelectionEntry

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MapSelection(navController: NavController) {
    FlowRow(modifier = Modifier.fillMaxSize()) {
        MapSelectionEntry("AT", "Austria", onClick = {
            navController.navigate("austriaMap")
        })
    }
}