package com.example.atquiz.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atquiz.composables.FullMap
import com.example.atquiz.models.MapPart
import kotlin.random.Random

@Composable
fun CardQuiz(partList: List<MapPart>) {

    var options by remember {
        mutableStateOf(partList.map { it.name })
    }

    val optionsLength = partList.size

    var correctCount by remember {
        mutableIntStateOf(0)
    }

    var isCorrect by remember {
        mutableStateOf(false)
    }

    var mapIsDisabled by remember {
        mutableStateOf(false)
    }

    Log.e("ALARM", "ALARM")
    var selectedOption by remember {
        mutableStateOf(options[Random.nextInt(options.size)])
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.8f)
                .padding(10.dp)
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            FullMap(partList = partList, buttonsDisabled = mapIsDisabled, onclick = { name ->
                if(name == selectedOption) {
                    Log.e("ALARM", "ALARM")
                    correctCount++
                    isCorrect = true
                    mapIsDisabled = true
                    options = options.filter { it != name }
                } else {
                    isCorrect = false
                }
            })
        }
        Column(modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
            if(isCorrect && options.isNotEmpty()) {
                Text(text = "Correct!", fontSize = 20.sp)
            } else if(!isCorrect && options.isNotEmpty()) {
                Text(text="Click on $selectedOption", fontSize = 20.sp)
            }
            if(options.isEmpty()) {
                Text(text = "You got $correctCount/$optionsLength  correct answers!")
            }
            Button(enabled = isCorrect && options.isNotEmpty(), onClick = {
                if(options.isNotEmpty()) {
                    selectedOption = options[Random.nextInt(options.size)]
                    mapIsDisabled = false
                }
                Log.e("LENGTH", options.size.toString())
                isCorrect = false

            }) {
                Text(text = "Next")
            }
        }

    }

}