package com.example.atquiz.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atquiz.models.AUSTRIA_PARTS
import com.example.atquiz.models.QuestionObject
import com.example.atquiz.screens.CardQuiz
import com.example.atquiz.screens.MapSelection
import com.example.atquiz.ui.theme.primary
import com.example.atquiz.ui.theme.secondary
import com.example.atquiz.ui.theme.text


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuizBuilder(
    questionList: List<QuestionObject>,
    onUpdateQuestionList: (newList : List<QuestionObject>) -> Unit,
    navController : NavController
) {

    val tagList: List<String> = questionList.map { it.tag }.distinct()
    val selectedTags = remember { mutableStateListOf<String>() }

    val filteredQuestions = if (selectedTags.isNotEmpty()) {
        questionList.filter { it.tag in selectedTags }
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
                    selected = chipData in selectedTags,
                    onClick = {
                        if (chipData in selectedTags) {
                            selectedTags.remove(chipData)
                        } else {
                            selectedTags.add(chipData)
                        }

                        onUpdateQuestionList(filteredQuestions)
                    },
                    label = { Text(text = chipData) },
                    modifier = Modifier.padding(horizontal = 4.dp)                 )
            }
        }

        // TODO: Leanders Map Choice buttons nehmen
        FlowRow(modifier = Modifier.fillMaxSize()) {
            MapSelectionEntry("5", "Fragen", onClick = {

                navController.navigate("questions")
                onUpdateQuestionList(filteredQuestions.shuffled().take(5))

            })


            MapSelectionEntry("10", "Fragen", onClick = {
                navController.navigate("questions")
                onUpdateQuestionList(filteredQuestions.shuffled().take(10))

            })

            MapSelectionEntry("15", "Fragen", onClick = {
                navController.navigate("questions")
                onUpdateQuestionList(filteredQuestions.shuffled().take(15))

            })

            MapSelectionEntry("20", "Fragen", onClick = {
                navController.navigate("questions")
                onUpdateQuestionList(filteredQuestions.shuffled().take(20))

            })
        }
    }
}

@Composable
fun QuestionList(filteredQuestions : List<QuestionObject>) {

    var currentQuestionIndex by remember { mutableIntStateOf(0) }

    val listState = rememberLazyListState()

    LaunchedEffect(currentQuestionIndex) {
        listState.animateScrollToItem(currentQuestionIndex)
    }


    // Frageliste mit Antworten
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        state = listState
    ) {
        itemsIndexed(filteredQuestions) { index, questionObject ->
            AnimatedVisibility( index <= currentQuestionIndex) {
                QuestionDetail(
                    questionObject = questionObject,
                    onAnswered = {
                        if (index == currentQuestionIndex && questionObject.answered) {
                            if (currentQuestionIndex < filteredQuestions.size - 1) {
                                currentQuestionIndex++
                            }
                        }

                    },
                    isCurrentQuestion = index == currentQuestionIndex,
                )
            }
        }
    }
}




@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetail(
    questionObject: QuestionObject,
    isCurrentQuestion: Boolean,
    onAnswered: () -> Unit
){
    val answers = questionObject.answerList
    val context = LocalContext.current
    var answerVisible by remember {
        mutableStateOf(false)
    }

    var containerColor by remember {
        mutableStateOf(secondary)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)

    ) {
        // Frage
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = text
            ),
            onClick = {
                answerVisible = !answerVisible
            }
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

        AnimatedVisibility(answerVisible) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                // Antwortmöglichkeiten
                answers.forEach { answer ->
                    Button(
                        onClick = {

                            if (questionObject.checkAnswer(answer)) {
                                containerColor = Color.Green
                            } else {
                                containerColor = Color.Red
                            }

                            answerVisible = false
                            onAnswered()
                        },
                        enabled = isCurrentQuestion && !questionObject.answered,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = text,
                            containerColor = if (questionObject.answered) secondary else primary
                        ),
                        shape = RoundedCornerShape(20),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(answer)
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MultipleChoiceQuiz(){
    // Possible API: https://opentdb.com/api_config.php
    // Possible Dataset: https://github.com/uberspot/OpenTriviaQA/

    val navController = rememberNavController()

    var questionList by remember {
        mutableStateOf(sampleQuestions)
    };

    NavHost(navController, startDestination = "quizbuilder"){
        composable("quizbuilder") {
            QuizBuilder(
                questionList = questionList,
                onUpdateQuestionList = {newList ->
                    questionList = newList
                },
                navController = navController
            )
        }

        composable("questions") {
            QuestionList(questionList)

        }
    }




}





val sampleQuestions = listOf(
    QuestionObject(
        question = "What is the capital of France?",
        answerList = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswerIndex = 2,
        tag = "Geography",
        answered = false

    ),
    QuestionObject(
        question = "Which planet is known as the Red Planet?",
        answerList = listOf("Earth", "Mars", "Jupiter", "Venus"),
        correctAnswerIndex = 1,
        tag = "Astronomy",
        answered = false

    ),
    QuestionObject(
        question = "What is the largest mammal?",
        answerList = listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
        correctAnswerIndex = 1,
        tag = "Biology",
        answered = false

    ),
    QuestionObject(
        question = "How many continents are there on Earth?",
        answerList = listOf("Five", "Six", "Seven", "Eight"),
        correctAnswerIndex = 2,
        tag = "Geography",
        answered = false

    ),
    QuestionObject(
        question = "What is the chemical symbol for water?",
        answerList = listOf("H2O", "O2", "CO2", "NaCl"),
        correctAnswerIndex = 0,
        tag = "Chemistry",
        answered = false

    ),
    QuestionObject(
        question = "Who wrote 'Romeo and Juliet'?",
        answerList = listOf("Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"),
        correctAnswerIndex = 2,
        tag = "Literature",
        answered = false

    ),
    QuestionObject(
        question = "Which language is primarily spoken in Brazil?",
        answerList = listOf("Spanish", "Portuguese", "French", "Italian"),
        correctAnswerIndex = 1,
        tag = "Language",
        answered = false

    ),
    QuestionObject(
        question = "What is 9 multiplied by 7?",
        answerList = listOf("56", "63", "72", "81"),
        correctAnswerIndex = 1,
        tag = "Mathematics",
        answered = false

    ),
    QuestionObject(
        question = "Which gas do plants absorb from the atmosphere?",
        answerList = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"),
        correctAnswerIndex = 2,
        tag = "Biology",
        answered = false
    ),
    QuestionObject(
        question = "In which year did the Titanic sink?",
        answerList = listOf("1905", "1912", "1918", "1923"),
        correctAnswerIndex = 1,
        tag = "History",
        answered = false

    )
)
