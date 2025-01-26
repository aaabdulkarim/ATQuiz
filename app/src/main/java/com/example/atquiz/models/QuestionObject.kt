package com.example.atquiz.models

class QuestionObject(val question : String, val answerList : List<String>, val correctAnswerIndex : Int, val tag : String, var answered : Boolean){

    fun checkAnswer(userAnswer : String): Boolean {
        answered = true
        return userAnswer == answerList[correctAnswerIndex];
    }
}