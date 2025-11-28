package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.data.services.Preferences
import org.smartmenu.project.models.*

class AdmonViewModel : ViewModel() {

    // ================================
    // LISTA DE USUARIOS
    // ================================
    private val _usersList = mutableStateOf<List<UsersResponse>>(emptyList())
    val usersList: State<List<UsersResponse>> = _usersList

    private val _selectedUser = mutableStateOf<UsersResponse?>(null)
    val selectedUser: State<UsersResponse?> = _selectedUser

    // ================================
    // LISTA DE PROVEEDORES
    // ================================
    private val _suppliersList = mutableStateOf<List<ProveedorResponse>>(emptyList())
    val suppliersList: State<List<ProveedorResponse>> = _suppliersList

    private val _selectedSupplier = mutableStateOf<ProveedorResponse?>(null)
    val selectedSupplier: State<ProveedorResponse?> = _selectedSupplier

    // ================================
    // ROLES
    // ================================
    private val _rolesList = mutableStateOf<List<RolesResponse>>(emptyList())
    val rolesList: State<List<RolesResponse>> = _rolesList

    // ================================
    // ESTADOS (Usuarios)
    // ================================
    private val _userCreatedSuccessfully = mutableStateOf(false)
    val userCreatedSuccessfully: State<Boolean> = _userCreatedSuccessfully

    private val _userCreateError = mutableStateOf<String?>(null)
    val userCreateError: State<String?> = _userCreateError

    private val _userUpdateSuccess = mutableStateOf(false)
    val userUpdateSuccess: State<Boolean> = _userUpdateSuccess

    private val _userUpdateError = mutableStateOf<String?>(null)
    val userUpdateError: State<String?> = _userUpdateError

    // ================================
    // ESTADOS (Proveedores)
    // ================================
    private val _supplierUpdateSuccess = mutableStateOf(false)
    val supplierUpdateSuccess: State<Boolean> = _supplierUpdateSuccess

    private val _supplierUpdateError = mutableStateOf<String?>(null)
    val supplierUpdateError: State<String?> = _supplierUpdateError
    // CREAR
    private val _supplierCreateSuccess = mutableStateOf(false)
    val supplierCreateSuccess: State<Boolean> = _supplierCreateSuccess

    private val _supplierCreateError = mutableStateOf<String?>(null)
    val supplierCreateError: State<String?> = _supplierCreateError


    // ================================
    // SERVICIO
    // ================================
    private val admonService = KtorfitFactory.getAdmonService()
    private val token = Preferences.getAuthToken()

    init {
        getRoles()
    }

    // ============================================================
    // ROLES
    // ============================================================
    fun getRoles() {
        viewModelScope.launch {
            try {
                val response = admonService.getRoles("Bearer $token")
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
                admonService.getClientes("Bearer $token")
            } catch (e: Exception) {
                println("Error fetching clients: ${e.message}")
            }
        }
    }

    // ============================================================
    // USUARIOS CRUD
    // ============================================================
    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = admonService.getUsuarios("Bearer $token")
                _usersList.value = response
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.getUserById("Bearer $token", userId)
                _selectedUser.value = response
            } catch (e: Exception) {
                println("Error fetching user by ID: ${e.message}")
            }
        }
    }

    fun newUser(nombre: String, usuario: String, contraseña: String, rol_id: Int) {
        viewModelScope.launch {
            try {
                val request = NewUserBody(nombre, usuario, contraseña, rol_id)

                val response = admonService.createUser("Bearer $token", request)

                if (response.error != null) {
                    _userCreateError.value = response.error
                } else {
                    _userCreatedSuccessfully.value = true
                }

            } catch (e: Exception) {
                _userCreateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun updateUser(id: Int, nombre: String, usuario: String, contraseña: String?, rol_id: Int) {
        viewModelScope.launch {
            try {
                val request = NewUserBody(nombre, usuario, contraseña, rol_id)
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

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.deleteUser("Bearer $token", id)

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

    fun resetCreateStates() {
        _userCreatedSuccessfully.value = false
        _userCreateError.value = null
    }

    // RESETEAR ESTADOS (Compatibilidad con RegisterScreen)
    fun resetSuccessState() {
        _userCreatedSuccessfully.value = false
    }

    fun resetErrorState() {
        _userCreateError.value = null
    }


    // ============================================================
    // PROVEEDORES CRUD
    // ============================================================
    fun getProveedores() {
        viewModelScope.launch {
            try {
                val response = admonService.getProveedores("Bearer $token")
                _suppliersList.value = response
            } catch (e: Exception) {
                println("Error fetching proveedores: ${e.message}")
            }
        }
    }

    fun getProveedorById(id: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.getProveedorById(id)
                _selectedSupplier.value = response
            } catch (e: Exception) {
                println("Error fetching proveedor by ID: ${e.message}")
            }
        }
    }

    fun updateProveedor(id: Int, nombre: String, contacto: String, telefono: String, correo: String) {
        viewModelScope.launch {
            try {
                val request = NewSupplierBody(nombre, contacto, telefono, correo)

                val response = admonService.updateProveedor(
                    "Bearer $token", request, id
                )

                if (response.error != null) {
                    _supplierUpdateError.value = response.error
                } else {
                    _supplierUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _supplierUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun newSupplier(nombre: String, contacto: String, telefono: String, correo: String) {
        viewModelScope.launch {
            try {
                val request = NewSupplierBody(nombre, contacto, telefono, correo)

                val response = admonService.createProveedor("Bearer $token", request)

                if (response.error != null) {
                    _supplierCreateError.value = response.error
                } else {
                    _supplierCreateSuccess.value = true
                }

            } catch (e: Exception) {
                _supplierCreateError.value = e.message ?: "Error desconocido"
            }
        }
    }


    fun resetSupplierCreateStates() {
        _supplierCreateSuccess.value = false
        _supplierCreateError.value = null
    }

    fun deleteProveedor(id: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.deleteProveedor("Bearer $token", id)

                if (response.error != null) {
                    _supplierUpdateError.value = response.error
                } else {
                    _supplierUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _supplierUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun resetSupplierUpdateStates() {
        _supplierUpdateSuccess.value = false
        _supplierUpdateError.value = null
    }
}
