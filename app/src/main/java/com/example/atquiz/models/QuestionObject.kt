package com.example.atquiz.models

import androidx.compose.ui.graphics.Color
import com.example.atquiz.ui.theme.secondary

class QuestionObject(val question : String, val answerList : List<String>, val correctAnswerIndex : Int, val tag : String, var answered : Boolean){

    var answeredCorrectly = false
    var containerColor = secondary

    fun checkAnswer(userAnswer : String): Boolean {
        answered = true
        if (userAnswer == answerList[correctAnswerIndex]) {
            answeredCorrectly = true
            containerColor = Color.Green
        } else {
            answeredCorrectly = false
            containerColor = Color.Red
        }

        return answeredCorrectly;
    }

    fun reset() {
        answered = false
        answeredCorrectly = false
        containerColor = secondary
    }
}