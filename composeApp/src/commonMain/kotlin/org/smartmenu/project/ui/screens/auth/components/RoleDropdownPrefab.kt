package org.smartmenu.project.ui.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.smartmenu.project.models.RolesResponse

@OptIn(ExperimentalMaterial3Api::class) // Necesario para ExposedDropdownMenuBox
@Composable
fun RoleDropdownPrefab(
    label: String = "Rol",
    roles: List<RolesResponse>,
    selectedRole: RolesResponse?,
    onRoleSelected: (RolesResponse) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    // Contenedor especial para menús desplegables
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
    ) {
        // TextField anclado al menú
        TextField(
            value = selectedRole?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            placeholder = { Text("Selecciona un rol") },
            singleLine = true,
            // Este modificador es CLAVE: conecta el TextField con el menú
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(80.dp) // Altura estándar un poco más cómoda
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                // La flechita giratoria automática
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        // El menú desplegable
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(colors.surface) // Fondo del menú
                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados del menú
        ) {
            roles.forEach { rol ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = rol.nombre,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(8.dp) // Espacio interno
                        )
                    },
                    onClick = {
                        onRoleSelected(rol)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
