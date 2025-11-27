package org.smartmenu.project.ui.screens.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.RegisterScreenRoute
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.viewmodels.AdmonViewModel

@Composable
fun UserHome(navController: NavController, innerPadding: PaddingValues) {
    val admonVm: AdmonViewModel = viewModel()
    val scope = rememberCoroutineScope()
    Column {
        Button(
            onClick = {
                scope.launch {
                    admonVm.getUsers()
                }
            },
        ){
            Text("Todos los usuarios")
        }
        // Modificar usuario
        Button(
            onClick = {
                scope.launch {
                    admonVm.getUsers()
                }
            },
        ){
            Text("Modificar usuario")
        }
        // Crear usuario
        Button(
            onClick = {
                navController.navigate(RegisterScreenRoute) {
                    popUpTo(UsersScreenRoute) { inclusive = true }
                }
            },
        ){
            Text("Crear usuario")
        }
        // Eliminar usuario
    }
}