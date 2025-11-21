package org.smartmenu.project.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.ui.SmartMenuTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit = {}
){
    val colors = MaterialTheme.colorScheme
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary,
            contentColor = colors.onPrimary
        ),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ActionButtonPreview(){
    SmartMenuTheme {
        ActionButton(
            text = "Action Button"
        )
    }
}