package org.smartmenu.project.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.smartmenu.project.ui.icons.EyeClosed
import org.smartmenu.project.ui.icons.EyeOpen

@Composable
fun HiddenTextField(
    text : String,
    value : String,
    onValueChange : (String) -> Unit,
    showPassword : Boolean,
    onShowPasswordChange: () -> Unit,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions? = null
){
    val colors = MaterialTheme.colorScheme
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(16.dp),
        label = { Text(text = text) },
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onShowPasswordChange) {
                Icon(
                    imageVector = if (showPassword) EyeOpen else EyeClosed,
                    contentDescription = if (showPassword) "Ocultar" else "Mostrar",
                    tint = colors.primary
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions ?: KeyboardActions(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
            .height(80.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = colors.onSurface,
            unfocusedTextColor = colors.onSurface,
            disabledTextColor = colors.onSurface.copy(alpha = 0.6f),
            cursorColor = colors.primary,
            focusedIndicatorColor = colors.primary,
            unfocusedIndicatorColor = colors.outline,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = colors.surfaceVariant,
            unfocusedContainerColor = colors.surface,
            focusedLeadingIconColor = colors.onSurface.copy(alpha = 0.9f),
            unfocusedLeadingIconColor = colors.onSurface.copy(alpha = 0.9f),
            focusedTrailingIconColor = colors.primary,
            unfocusedTrailingIconColor = colors.primary,
            focusedPlaceholderColor = colors.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = colors.onSurface.copy(alpha = 0.6f),
            focusedLabelColor = colors.onSurface,
            unfocusedLabelColor = colors.onSurface.copy(alpha = 0.9f)
        )
    )
}