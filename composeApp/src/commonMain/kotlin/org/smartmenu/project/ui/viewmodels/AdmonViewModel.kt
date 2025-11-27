package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.data.services.Preferences
import org.smartmenu.project.models.NewUserBody
import org.smartmenu.project.models.NewUserResponse
import org.smartmenu.project.models.RolesResponse
import org.smartmenu.project.models.UsersResponse

class AdmonViewModel : ViewModel() {

    // LISTA DE USUARIOS
    private  val _usersList = mutableStateOf<List<UsersResponse>>(emptyList())
    val usersList: State<List<UsersResponse>> = _usersList

    // USUARIO POR ID
    private val _selectedUser = mutableStateOf<UsersResponse?>(null)
    val selectedUser: State<UsersResponse?> = _selectedUser


    // LISTA DE ROLES
    private val _rolesList = mutableStateOf<List<RolesResponse>>(emptyList())
    val rolesList: State<List<RolesResponse>> = _rolesList

    // ESTADO DE ÉXITO
    private val _userCreatedSuccessfully = mutableStateOf(false)
    val userCreatedSuccessfully: State<Boolean> = _userCreatedSuccessfully
    private val _userUpdateSuccess = mutableStateOf(false)
    val userUpdateSuccess: State<Boolean> = _userUpdateSuccess

    // ESTADO DE ERROR
    private val _userCreateError = mutableStateOf<String?>(null)
    val userCreateError: State<String?> = _userCreateError
    private val _userUpdateError = mutableStateOf<String?>(null)
    val userUpdateError: State<String?> = _userUpdateError

    // SERVICIO
    private val admonService = KtorfitFactory.getAdmonService()
    private val token = Preferences.getAuthToken()

    init {
        getRoles()
//        getUsers()
    }

    // ============================================================
    // ROLES
    // ============================================================
    fun getRoles() {
        viewModelScope.launch {
            try {
                val response = admonService.getRoles("Bearer $token")
                println("Roles fetched successfully: $response")
                _rolesList.value = response
            } catch (e: Exception) {
                println("Error fetching roles: ${e.message}")
            }
        }
    }

    // ============================================================
    // CLIENTES
    // ============================================================
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

    // ============================================================
    // USUARIOS
    // ============================================================
    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = admonService.getUsuarios("Bearer $token")
                println("Users fetched successfully: $response")
                _usersList.value = response
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

    // ============================================================
    // PROVEEDORES
    // ============================================================
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

    // ============================================================
    // CREAR NUEVO USUARIO
    // ============================================================
    fun newUser(nombre: String, usuario: String, contraseña: String, rol_id: Int) {
        viewModelScope.launch {
            try {
                val request = NewUserBody(
                    nombre = nombre,
                    usuario = usuario,
                    contraseña = contraseña,
                    rol_id = rol_id
                )

                val response: NewUserResponse =
                    admonService.createUser("Bearer $token", request)

                println("CreateUser Response: $response")

                if (response.error != null) {
                    // ERROR
                    _userCreateError.value = response.error
                } else {
                    // ÉXITO
                    _userCreatedSuccessfully.value = true
                }

            } catch (e: Exception) {
                println("Error creating user: ${e.message}")
                _userCreateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    // RESETEAR ESTADOS
    fun resetSuccessState() {
        _userCreatedSuccessfully.value = false
    }

    fun resetErrorState() {
        _userCreateError.value = null
    }

    // ============================================================
    // ACTUALIZAR USUARIO
    // ============================================================

    fun updateUser(
        id: Int,
        nombre: String,
        usuario: String,
        contraseña: String?,
        rol_id: Int
    ) {
        viewModelScope.launch {
            try {
                val request = NewUserBody(
                    nombre = nombre,
                    usuario = usuario,
                    contraseña = contraseña,
                    rol_id = rol_id
                )

                val response = admonService.editUser("Bearer $token", request, id)

                if (response.error != null) {
                    _userUpdateError.value = response.error
                } else {
                    _userUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _userUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun resetUpdateStates() {
        _userUpdateSuccess.value = false
        _userUpdateError.value = null
    }


    fun getUserById(userId: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.getUserById("Bearer $token", userId)
                println("User fetched successfully: $response")
                _selectedUser.value = response
            } catch (e: Exception) {
                println("Error fetching user by ID: ${e.message}")
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.deleteUser("Bearer $token", userId)

                if (response.error != null) {
                    _userUpdateError.value = response.error
                } else {
                    _userUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _userUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

}
