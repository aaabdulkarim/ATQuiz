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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun QuestionFilter(
    questionList: List<QuestionObject>,
    onShowDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    updateQuestionObject: (QuestionObject) -> Unit,

) {

    // das "with" bedeutet man benutzt die Daten und Funktionen eines Objekts
    // ohne auf sie zu referenzieren, wie im Beispiel beim .sharedElementModifier
    with(sharedTransitionScope) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .sharedElement(
                    rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(questionList) { index, question ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = secondary,
                        contentColor = text
                    ),
                    onClick = {
                        updateQuestionObject(questionList[index])
                        onShowDetails()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = question.question,
                            color = text,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun QuestionDetail(
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    questionObject: QuestionObject,
){
    val answers = questionObject.answerList
    val context = LocalContext.current

    // das "with" bedeutet man benutzt die Daten und Funktionen eines Objekts
    // ohne auf sie zu referenzieren, wie im Beispiel beim .sharedElement-Modifier
    with(sharedTransitionScope) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .sharedElement(
                    rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope
                )

        ) {
            Text(text = questionObject.question)
            answers.forEach { answer ->
                Button(
                    onClick = {

                        val toastString =
                            if (questionObject.checkAnswer(answer)) "Richtig!" else "Falsch"
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
            Button(onClick = { onBack() }) {
                Text(text = "Zurück")
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MultipleChoiceQuiz(){
    // Possible API: https://opentdb.com/api_config.php
    // Possible Dataset: https://github.com/uberspot/OpenTriviaQA/
    val questionList = sampleQuestions;
    var questionObject by remember{ mutableStateOf(questionList[0]) }

    // Für Shared Element Transition
    var showDetails by remember {
        mutableStateOf(false)
    }

    SharedTransitionLayout {
        AnimatedContent(
            showDetails,
            label = "basic_transition"
        ) { targetState ->
            if (!targetState) {
                QuestionFilter(

                    onShowDetails = {
                        showDetails = true
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    questionList = questionList,
                ){
                    questionObject = it
                }
            } else {
                QuestionDetail(
                    onBack = {
                        showDetails = false
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    questionObject = questionObject
                )
            }
        }
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

