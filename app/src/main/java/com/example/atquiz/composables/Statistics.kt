package com.example.atquiz.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars


// Diese Data Class sollten die Daten der Statistiken bestmöglichst halten
//
data class StatUnit(
    val statLabel : String,
    val amountQuestion : Int,
    val amountCorrect : Int
)


// Chart Benutzen: https://github.com/ehsannarmani/ComposeCharts/tree/0.0.4?tab=readme-ov-file#gradle-setup

@Composable
fun Statistics(statisticList : List<StatUnit>) {

    val dataList = statisticList.map {
        Bars (
            label = it.statLabel,
            values = listOf(
                Bars.Data(label = "Insgesamt", value = it.amountQuestion.toDouble(), color = SolidColor(Color.Red)),
                Bars.Data(label = "Korrekt", value = it.amountQuestion.toDouble(), color = SolidColor(Color.Green)),
            )
        )
    }

    ColumnChart(
        modifier= Modifier.fillMaxSize().padding(horizontal = 22.dp),
        data = dataList,

        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = 3.dp,
            thickness  = 20.dp
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )
}


val sampleStatUnitList = listOf(
    StatUnit(
        statLabel = "Quiz 1",
        amountQuestion = 10,
        amountCorrect = 7
    ),
    StatUnit(
        statLabel = "Quiz 2",
        amountQuestion = 15,
        amountCorrect = 12
    ),
    StatUnit(
        statLabel = "Quiz 3",
        amountQuestion = 8,
        amountCorrect = 5
    ),
    StatUnit(
        statLabel = "Quiz 4",
        amountQuestion = 20,
        amountCorrect = 18
    ),
    StatUnit(
        statLabel = "Quiz 5",
        amountQuestion = 25,
        amountCorrect = 23
    )
)
