package com.example.atquiz.composables

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.atquiz.ui.theme.primary
import com.example.atquiz.ui.theme.secondary
import com.example.atquiz.ui.theme.text


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionFilter(
    questionList: List<QuestionObject>,
) {
    var selectedTag by remember {
        mutableStateOf("")
    }

    // Extract distinct tags from the question list
    val tagList: List<String> = questionList.map { it.tag }.distinct()

    val filteredQuestions = if (selectedTag.isNotEmpty()) {
        questionList.filter { it.tag == selectedTag }
    } else {
        questionList
    }

    Column {
        // Tags
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(tagList) { _, chipData ->
                InputChip(
                    selected = selectedTag == chipData,
                    onClick = {
                        if (selectedTag == chipData) {
                            selectedTag = ""
                        } else {
                            selectedTag = chipData
                        }
                    },
                    label = { Text(text = chipData) },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}




@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun QuestionDetail(
    questionObject: QuestionObject,
) {
    val answers = questionObject.answerList
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(2.dp)

    ) {
        // Frage
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = secondary,
                contentColor = text
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = questionObject.question,
                    color = text,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        // Antwortmöglichkeiten
        answers.forEach { answer ->
            Button(
                onClick = {

                    val toastString =
                        if (questionObject.checkAnswer(answer)) "Richtig" else "Falsch"
                    Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()
                },

                colors = ButtonDefaults.buttonColors(
                    contentColor = text,
                    containerColor = primary
                ),
                shape = RoundedCornerShape(20),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(answer)
            }
        }
    }

}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MultipleChoiceQuiz() {
    // Possible Dataset: https://github.com/uberspot/OpenTriviaQA/
    val questionList = sampleQuestions;
    var currentQuestionIndex by remember {
        mutableStateOf(0)
    }
    var questionObject by remember { mutableStateOf(questionList[currentQuestionIndex]) }

    Column {
        QuestionFilter(questionList)

        QuestionDetail(
            questionObject = questionObject
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--
                    questionObject = questionList[currentQuestionIndex]
                }
            }) {
                Text("Back")
            }

            Button(onClick = {
                if (currentQuestionIndex < questionList.size - 1) {
                    currentQuestionIndex++
                    questionObject = questionList[currentQuestionIndex]
                }
            }) {
                Text("Next")
            }
        }
    }
}


data class QuestionObject(
    val question: String,
    val answerList: List<String>,
    val correctAnswerIndex: Int,
    val tag: String
) {

    fun checkAnswer(userAnswer: String): Boolean {
        return userAnswer == answerList[correctAnswerIndex];
    }
}


val sampleQuestions = listOf(
    QuestionObject(
        question = "What is the capital of France?",
        answerList = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswerIndex = 2,
        tag = "Geography"
    ),
    QuestionObject(
        question = "Which planet is known as the Red Planet?",
        answerList = listOf("Earth", "Mars", "Jupiter", "Venus"),
        correctAnswerIndex = 1,
        tag = "Science"
    ),
    QuestionObject(
        question = "What is the largest mammal?",
        answerList = listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
        correctAnswerIndex = 1,
        tag = "Biology"
    ),
    QuestionObject(
        question = "How many continents are there on Earth?",
        answerList = listOf("Five", "Six", "Seven", "Eight"),
        correctAnswerIndex = 2,
        tag = "Geography"
    ),
    QuestionObject(
        question = "What is the chemical symbol for water?",
        answerList = listOf("H2O", "O2", "CO2", "NaCl"),
        correctAnswerIndex = 0,
        tag = "Chemistry"
    ),
    QuestionObject(
        question = "Who wrote 'Romeo and Juliet'?",
        answerList = listOf("Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"),
        correctAnswerIndex = 2,
        tag = "Literature"
    ),
    QuestionObject(
        question = "Which language is primarily spoken in Brazil?",
        answerList = listOf("Spanish", "Portuguese", "French", "Italian"),
        correctAnswerIndex = 1,
        tag = "Language"
    ),
    QuestionObject(
        question = "What is 9 multiplied by 7?",
        answerList = listOf("56", "63", "72", "81"),
        correctAnswerIndex = 1,
        tag = "Mathematics"
    ),
    QuestionObject(
        question = "Which gas do plants absorb from the atmosphere?",
        answerList = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"),
        correctAnswerIndex = 2,
        tag = "Biology"
    ),
    QuestionObject(
        question = "In which year did the Titanic sink?",
        answerList = listOf("1905", "1912", "1918", "1923"),
        correctAnswerIndex = 1,
        tag = "History"
    )
)

