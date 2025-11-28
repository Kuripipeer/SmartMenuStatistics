package org.smartmenu.project.ui.screens.home.components.charts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.SmartMenuTheme
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.put

data class StockRow(
    val nombre: String,
    val stock: String,
    val stockMinimo: String
)

@Composable
fun TableChart(grafica: Grafica) {
    val colors = MaterialTheme.colorScheme

    // Parseamos los JsonElement a filas tipadas
    val rows = remember(grafica) {
        grafica.values.mapNotNull { element: JsonElement ->
            val obj = element.jsonObject

            val nombre = obj["nombre"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val stock = obj["stock"]?.jsonPrimitive?.contentOrNull ?: "-"
            val stockMinimo = obj["stock_minimo"]?.jsonPrimitive?.contentOrNull ?: "-"

            StockRow(nombre, stock, stockMinimo)
        }
    }

    if (rows.isEmpty()) {
        Text(
            text = "Sin datos",
            style = MaterialTheme.typography.bodySmall,
            color = colors.onSurfaceVariant
        )
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Encabezado
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = colors.surfaceVariant,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Insumo",
                    modifier = Modifier.weight(2f),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant
                )
                Text(
                    text = "Stock",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant
                )
                Text(
                    text = "Mínimo",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant
                )
            }
        }

        // Filas
        val lastIndex = rows.lastIndex
        rows.forEachIndexed { index, row ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = if (index % 2 == 0) colors.surface
                else colors.surfaceVariant.copy(alpha = 0.35f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = row.nombre,
                        modifier = Modifier.weight(2f),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurface
                    )
                    Text(
                        text = row.stock,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurface
                    )
                    Text(
                        text = row.stockMinimo,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurface
                    )
                }
            }

            if (index < lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = colors.outline.copy(alpha = 0.4f),
                    thickness = 0.5.dp
                )
            }
        }
    }
}
