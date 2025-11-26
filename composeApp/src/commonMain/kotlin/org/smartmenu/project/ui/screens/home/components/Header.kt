package org.smartmenu.project.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import smartmenustatistics.composeapp.generated.resources.Res
import smartmenustatistics.composeapp.generated.resources.logo

@Composable
fun Header(navController: NavController) {
    val colors = MaterialTheme.colorScheme
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = colors.primary,
                modifier =
                    Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color = Color.White.copy(alpha = 0.7f))
            )
        }
        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.headlineSmall,
            color = colors.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Regresar",
                tint = colors.primary,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(color = Color.White.copy(alpha = 0.7f))
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Juan Pérez",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = " - Gerente",
            style = MaterialTheme.typography.titleMedium
        )
    }
}