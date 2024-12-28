package com.example.atquiz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atquiz.ui.theme.ATQuizTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            ATQuizTheme {
                Scaffold(
                    bottomBar = {
                        AppNavigationBar(navController)
                    },

                ) {innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation(navController)
                    }
                }


            }
        }
    }
}


@Composable
fun Statistik(){
    Text("Du hast 0 Punkte")
}

@Composable
fun CardQuiz() {
    Text("Card")
}



@Composable
fun AppNavigation(navController : NavHostController) {


    Column {
        NavHost(navController, startDestination = "karte"){
            composable("stat") {
                Statistik()
            }

            composable("karte"){
                CardQuiz()
            }

            composable("quiz") {
                MultipleChoiceQuiz()
            }
        }

        Spacer(Modifier.height(2.dp))


    }
}


@Composable
fun AppNavigationBar(navController : NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Karte", "Quiz", "Stat")
    val iconList = listOf(R.drawable.ic_map)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon= {
                    Icon(
                        painter = painterResource(id = iconList[index]),
                        contentDescription = "Local SVG"
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(items[index])

                }
            )
        }
    }
}


