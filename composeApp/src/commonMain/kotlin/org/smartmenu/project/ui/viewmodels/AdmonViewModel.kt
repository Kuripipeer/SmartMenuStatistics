package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.data.services.Preferences
import org.smartmenu.project.models.NewUserBody
import org.smartmenu.project.models.RolesResponse

class AdmonViewModel : ViewModel() {

    private val _rolesList = mutableStateOf<List<RolesResponse>>(emptyList())
    val rolesList: State<List<RolesResponse>> = _rolesList

    private val admonService = KtorfitFactory.getAdmonService()
    private val token = Preferences.getAuthToken()

    init {
        getRoles()
    }

    fun getRoles() {
        viewModelScope.launch {
            try {
                val response = admonService.getRoles("Bearer $token")
                println("Roles fetched successfully: $response")
                _rolesList.value = response     // <-- Aquí ya guardamos los roles en estado
            } catch (e: Exception) {
                println("Error fetching roles: ${e.message}")
            }
        }
    }

    fun getClients() {
        viewModelScope.launch {
            try {
                val response = admonService.getClientes("Bearer $token")
                println("Clients fetched successfully: $response")
            } catch (e: Exception) {
                println("Error fetching clients: ${e.message}")
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = admonService.getUsuarios("Bearer $token")
                println("Users fetched successfully: $response")
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

    fun getProveedores() {
        viewModelScope.launch {
            try {
                val response = admonService.getProveedores("Bearer $token")
                println("Proveedores fetched successfully: $response")
            } catch (e: Exception) {
                println("Error fetching proveedores: ${e.message}")
            }
        }
    }

    fun newUser(nombre: String, usuario: String, contraseña: String, rol_id: Int) {
        viewModelScope.launch {
            try {
                val request = NewUserBody(
                    nombre = nombre,
                    usuario = usuario,
                    contraseña = contraseña,
                    rol_id = rol_id
                )
                val response = admonService.createUser("Bearer $token", request)
                println("User created successfully: ${response.message}")

            } catch (e: Exception) {
                println("Error creating user: ${e.message}")
            }
        }
    }
}
