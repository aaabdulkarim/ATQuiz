package com.example.atquiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun QuestionFilter(questionList: List<QuestionObject>
               ,updateQuestionObject: (QuestionObject) -> Unit) {

    Column {
        Button(onClick = {
            val randomIndex = Random.nextInt(0, questionList.size - 1 )
            updateQuestionObject(questionList[randomIndex])
        }) {
            Text(text = "Frag mich")
        }
    }
}


@Composable
fun QuestionDetail(questonObject: QuestionObject){
    val answers = questonObject.answerList

    Column {
        Text(questonObject.question)
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(200.dp)
        ) {
            items(answers) { answer ->
                Button(onClick = {
                    questonObject.checkAnswer(answer)
                }) {
                    Text(answer)
                }
            }
        }
    }
}


@Composable
fun MultipleChoiceQuiz(){
    val questionList = sampleQuestions;
    var questionObject by remember{ mutableStateOf(questionList[0]) }

    Column {
        QuestionFilter(questionList){
            questionObject = it
        }
        Spacer(modifier = Modifier.height(2.dp))
        QuestionDetail(questionObject)
    }

}


data class QuestionObject(val question : String, val answerList : List<String>, val correctAnswerIndex : Int){

    fun checkAnswer(userAnswer : String): Boolean {
        return userAnswer == answerList[correctAnswerIndex];
    }
}


val sampleQuestions = listOf(
    QuestionObject(
        question = "What is the capital of France?",
        answerList = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswerIndex = 2
    ),
    QuestionObject(
        question = "Which planet is known as the Red Planet?",
        answerList = listOf("Earth", "Mars", "Jupiter", "Venus"),
        correctAnswerIndex = 1
    ),
    QuestionObject(
        question = "What is the largest mammal?",
        answerList = listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
        correctAnswerIndex = 1
    ),
    QuestionObject(
        question = "How many continents are there on Earth?",
        answerList = listOf("Five", "Six", "Seven", "Eight"),
        correctAnswerIndex = 2
    ),
    QuestionObject(
        question = "What is the chemical symbol for water?",
        answerList = listOf("H2O", "O2", "CO2", "NaCl"),
        correctAnswerIndex = 0
    ),
    QuestionObject(
        question = "Who wrote 'Romeo and Juliet'?",
        answerList = listOf("Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"),
        correctAnswerIndex = 2
    ),
    QuestionObject(
        question = "Which language is primarily spoken in Brazil?",
        answerList = listOf("Spanish", "Portuguese", "French", "Italian"),
        correctAnswerIndex = 1
    ),
    QuestionObject(
        question = "What is 9 multiplied by 7?",
        answerList = listOf("56", "63", "72", "81"),
        correctAnswerIndex = 1
    ),
    QuestionObject(
        question = "Which gas do plants absorb from the atmosphere?",
        answerList = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"),
        correctAnswerIndex = 2
    ),
    QuestionObject(
        question = "In which year did the Titanic sink?",
        answerList = listOf("1905", "1912", "1918", "1923"),
        correctAnswerIndex = 1
    )
)

