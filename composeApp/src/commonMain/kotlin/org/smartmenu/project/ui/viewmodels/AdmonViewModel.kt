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
    // LISTA DE CLIENTES
    // ================================
    private val _clientsList = mutableStateOf<List<ClientsResponseItem>>(emptyList())
    val clientsList: State<List<ClientsResponseItem>> = _clientsList

    private val _selectedClient = mutableStateOf<ClientsResponseItem?>(null)
    val selectedClient: State<ClientsResponseItem?> = _selectedClient

    // Estados clientes
    private val _clientCreateSuccess = mutableStateOf(false)
    val clientCreateSuccess: State<Boolean> = _clientCreateSuccess

    private val _clientCreateError = mutableStateOf<String?>(null)
    val clientCreateError: State<String?> = _clientCreateError

    private val _clientUpdateSuccess = mutableStateOf(false)
    val clientUpdateSuccess: State<Boolean> = _clientUpdateSuccess

    private val _clientUpdateError = mutableStateOf<String?>(null)
    val clientUpdateError: State<String?> = _clientUpdateError

    // ================================
    // LISTA DE PROVEEDORES
    // ================================
    private val _suppliersList = mutableStateOf<List<ProveedorResponse>>(emptyList())
    val suppliersList: State<List<ProveedorResponse>> = _suppliersList

    private val _selectedSupplier = mutableStateOf<ProveedorResponse?>(null)
    val selectedSupplier: State<ProveedorResponse?> = _selectedSupplier

    private val _supplierUpdateSuccess = mutableStateOf(false)
    val supplierUpdateSuccess: State<Boolean> = _supplierUpdateSuccess

    private val _supplierUpdateError = mutableStateOf<String?>(null)
    val supplierUpdateError: State<String?> = _supplierUpdateError

    private val _supplierCreateSuccess = mutableStateOf(false)
    val supplierCreateSuccess: State<Boolean> = _supplierCreateSuccess

    private val _supplierCreateError = mutableStateOf<String?>(null)
    val supplierCreateError: State<String?> = _supplierCreateError

    // ================================
    // ROLES
    // ================================
    private val _rolesList = mutableStateOf<List<RolesResponse>>(emptyList())
    val rolesList: State<List<RolesResponse>> = _rolesList

    // ================================
    // USUARIO Estados
    // ================================
    private val _userCreatedSuccessfully = mutableStateOf(false)
    val userCreatedSuccessfully: State<Boolean> = _userCreatedSuccessfully

    private val _userCreateError = mutableStateOf<String?>(null)
    val userCreateError: State<String?> = _userCreateError

    private val _userUpdateSuccess = mutableStateOf(false)
    val userUpdateSuccess: State<Boolean> = _userUpdateSuccess

    private val _userUpdateError = mutableStateOf<String?>(null)
    val userUpdateError: State<String?> = _userUpdateError


    // Servicio
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
                _rolesList.value = admonService.getRoles("Bearer $token")
            } catch (e: Exception) {
                println("Error fetching roles: ${e.message}")
            }
        }
    }

    // ============================================================
    // CLIENTES CRUD
    // ============================================================
    fun getClientes() {
        viewModelScope.launch {
            try {
                val response = admonService.getClientes("Bearer $token")
                _clientsList.value = response
            } catch (e: Exception) {
                println("Error fetching clients: ${e.message}")
            }
        }
    }

    fun newClient(nombre: String, telefono: String, correo: String) {
        viewModelScope.launch {
            try {
                val request = NewClientBody(nombre, telefono, correo)
                val response = admonService.createCliente("Bearer $token", request)

                if (response.error != null) {
                    _clientCreateError.value = response.error
                } else {
                    _clientCreateSuccess.value = true
                }

            } catch (e: Exception) {
                _clientCreateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun getClientById(id: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.getClientById("Bearer $token", id)
                _selectedClient.value = response
            } catch (e: Exception) {
                println("Error fetching client: ${e.message}")
            }
        }
    }

    fun updateClient(id: Int, nombre: String, telefono: String, correo: String) {
        viewModelScope.launch {
            try {
                val request = NewClientBody(nombre, telefono, correo)
                val response = admonService.updateCliente("Bearer $token", request, id)

                if (response.error != null) {
                    _clientUpdateError.value = response.error
                } else {
                    _clientUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _clientUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun deleteClient(id: Int) {
        viewModelScope.launch {
            try {
                val response = admonService.deleteCliente("Bearer $token", id)

                if (response.error != null) {
                    _clientUpdateError.value = response.error
                } else {
                    _clientUpdateSuccess.value = true
                }

            } catch (e: Exception) {
                _clientUpdateError.value = e.message ?: "Error desconocido"
            }
        }
    }

    fun resetClientCreateStates() {
        _clientCreateSuccess.value = false
        _clientCreateError.value = null
    }

    fun resetClientUpdateStates() {
        _clientUpdateSuccess.value = false
        _clientUpdateError.value = null
    }

    // ============================================================
    // USUARIOS CRUD
    // ============================================================
    fun getUsers() {
        viewModelScope.launch {
            try {
                _usersList.value = admonService.getUsuarios("Bearer $token")
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedUser.value = admonService.getUserById("Bearer $token", id)
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
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
                _suppliersList.value = admonService.getProveedores("Bearer $token")
            } catch (e: Exception) {
                println("Error fetching proveedores: ${e.message}")
            }
        }
    }

    fun getProveedorById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedSupplier.value = admonService.getProveedorById(id)
            } catch (e: Exception) {
                println("Error fetching proveedor: ${e.message}")
            }
        }
    }

    fun updateProveedor(id: Int, nombre: String, contacto: String, telefono: String, correo: String) {
        viewModelScope.launch {
            try {
                val request = NewSupplierBody(nombre, contacto, telefono, correo)
                val response = admonService.updateProveedor("Bearer $token", request, id)

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

    fun resetSupplierCreateStates() {
        _supplierCreateSuccess.value = false
        _supplierCreateError.value = null
    }

    fun resetSupplierUpdateStates() {
        _supplierUpdateSuccess.value = false
        _supplierUpdateError.value = null
    }
}
