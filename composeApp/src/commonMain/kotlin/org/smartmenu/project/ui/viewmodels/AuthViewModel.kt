package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.data.services.Preferences
import org.smartmenu.project.models.LoginBody

class AuthViewModel() : ViewModel() {
    val authService = KtorfitFactory.getAuthService()
    var isLogged by mutableStateOf(Preferences.getIsLogged())

    fun login(user : String, password : String){
        viewModelScope.launch {
            try {
                val request = LoginBody(
                    usuario = user,
                    contraseña = password
                )
//                val response = authService.login(request)

                val response = authService.login(request)

                if (response.token != null) {
                    // login exitoso
                    Preferences.saveAuthToken(response.token)
                    Preferences.saveIsLogged(true)
                    isLogged = true
                    println("Token: ${response.token}")
                } else {
                    println("Error: ${response.error ?: "Error desconocido"}")
                }



            }catch (e: Exception){
                println("Error en el login: ${e}")
            }
        }
    }
}