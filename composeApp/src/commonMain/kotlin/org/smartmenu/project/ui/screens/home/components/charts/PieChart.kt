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
import ir.ehsannarmani.compose_charts.models.Pie.Style
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.ChartColors
import org.smartmenu.project.ui.SmartMenuTheme
import androidx.compose.foundation.shape.CircleShape

@Composable
fun PieChart(grafica: Grafica) {

    val colorPalette = ChartColors
    val onTextColor = MaterialTheme.colorScheme.onBackground

    // Convertir JsonElement -> Double sin errores
    fun toDoubleSafe(el: JsonElement): Double {
        val prim = el.jsonPrimitive

        prim.doubleOrNull?.let { return it }
        prim.content.toDoubleOrNull()?.let { return it }

        return 0.0
    }

    // Convertir valores correctamente
    val numericValues = remember(grafica) {
        grafica.values.map { toDoubleSafe(it) }
    }

    val sliceColors = remember(grafica) {
        grafica.labels.mapIndexed { index, _ ->
            colorPalette[index % colorPalette.size]
        }
    }

    // Crear los slices
    var pies by remember(grafica) {
        mutableStateOf(
            grafica.labels.mapIndexed { index, label ->
                Pie(
                    label = label,
                    data = numericValues[index],
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

        // LEYENDA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            pies.forEach { pie ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(pie.color, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = pie.label ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = onTextColor
                    )
                }
            }
        }

        // PIE CHART
        ComposePieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            data = pies,
            onPieClick = { clicked ->
                pies = pies.mapIndexed { i, pie -> pie.copy(selected = clicked == pie) }
            },
            selectedScale = 1.07f,
            style = Style.Fill
        )
    }
}
//
//@Preview
//@Composable
//fun PieChartPreview() {
//    val grafica = Grafica(
//        tipo = "pie",
//        titulo = "Platillos Vendidos",
//        labels = listOf("Hamburguesa", "Ensalada", "Pizza"),
//        values = listOf(
//            kotlinx.serialization.json.JsonElement.serializer().deserialize(
//                kotlinx.serialization.json.Json,
//                "\"2\""
//            )
//        )
//    )
//    SmartMenuTheme {
//        PieChart(grafica)
//    }
//}
