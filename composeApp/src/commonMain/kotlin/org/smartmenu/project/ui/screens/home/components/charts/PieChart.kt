package org.smartmenu.project.ui.screens.home.components.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.PieChart as ComposePieChart
import ir.ehsannarmani.compose_charts.models.Pie
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.ChartColors

@Composable
fun PieChart(grafica: Grafica) {

    // Paleta oficial
    val colorPalette = ChartColors
    val onTextColor = MaterialTheme.colorScheme.onBackground

    // Colores asignados según label count
    val sliceColors = remember(grafica) {
        grafica.labels.mapIndexed { index, _ ->
            colorPalette[index % colorPalette.size]
        }
    }

    // Piezas del gráfico
    var pies by remember(grafica) {
        mutableStateOf(
            grafica.labels.zip(grafica.values).mapIndexed { index, (label, value) ->
                Pie(
                    label = label,
                    data = value.toDouble(),
                    color = sliceColors[index],
                    selectedColor = sliceColors[index].copy(alpha = 0.85f),
                    selected = false
                )
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ----------- LEYENDA ARRIBA -----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            pies.forEachIndexed { i, pie ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(pie.color, shape = androidx.compose.foundation.shape.CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    pie.label?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            color = onTextColor
                        )
                    }
                }
            }
        }

        // ----------- PIE CHART -----------
        ComposePieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            data = pies,
            onPieClick = { clicked ->
                val index = pies.indexOf(clicked)
                pies = pies.mapIndexed { i, pie -> pie.copy(selected = i == index) }
            },
            selectedScale = 1.07f,
            style = Pie.Style.Fill
        )
    }
}

@Preview
@Composable
fun PieChartPreview() {
    val grafica = Grafica(
        tipo = "pie",
        titulo = "Platillos Vendidos",
        labels = listOf("Hamburguesa", "Ensalada", "Pizza"),
        values = listOf(2, 1, 1)
    )
    SmartMenuTheme {
        PieChart(grafica)
    }
}
