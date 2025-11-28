package org.smartmenu.project.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import org.smartmenu.project.models.ClientsResponse
import org.smartmenu.project.models.ClientsResponseItem
import org.smartmenu.project.models.NewClientBody
import org.smartmenu.project.models.NewClientResponse
import org.smartmenu.project.models.NewSupplierBody
import org.smartmenu.project.models.NewSupplierResponse
import org.smartmenu.project.models.NewUserBody
import org.smartmenu.project.models.NewUserResponse
import org.smartmenu.project.models.ProveedorResponse
import org.smartmenu.project.models.Proveedores
import org.smartmenu.project.models.RolResponse
import org.smartmenu.project.models.UpdateClientResponse
import org.smartmenu.project.models.UserResponse
import org.smartmenu.project.models.UsersResponse

interface AdmonService {

    @GET("clientes")
    suspend fun getClientes(
        @Header("Authorization") token: String
    ): ClientsResponse

    @GET("usuarios")
    suspend fun getUsuarios(
        @Header("Authorization") token: String
    ): UserResponse

    @GET("usuarios/roles/all")
    suspend fun getRoles(
        @Header("Authorization") token: String
    ): RolResponse

    @GET("proveedores")
    suspend fun getProveedores(
        @Header("Authorization") token: String
    ): Proveedores

    @POST("usuarios")
    suspend fun createUser(
        @Header("Authorization") token: String,
        @Body request: NewUserBody
    ): NewUserResponse

    @PUT("usuarios/{id}")
    suspend fun editUser(
        @Header("Authorization") token: String,
        @Body request: NewUserBody,
        @Path("id") userId: Int
    ): NewUserResponse

    @GET("usuarios/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): UsersResponse

    @DELETE("usuarios/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): NewUserResponse

    @GET("proveedores/{id}")
    suspend fun getProveedorById(
        @Path("id") proveedorId: Int
    ): ProveedorResponse

    @POST("proveedores")
    suspend fun createProveedor(
        @Header("Authorization") token: String,
        @Body request: NewSupplierBody
    ): NewSupplierResponse

    @PUT("proveedores/{id}")
    suspend fun updateProveedor(
        @Header("Authorization") token: String,
        @Body request: NewSupplierBody,
        @Path("id") proveedorId: Int
    ): NewSupplierResponse

    @DELETE("proveedores/{id}")
    suspend fun deleteProveedor(
        @Header("Authorization") token: String,
        @Path("id") proveedorId: Int
    ): NewUserResponse

    @POST("clientes")
    suspend fun createCliente(
        @Header("Authorization") token: String,
        @Body request: NewClientBody
    ): NewClientResponse

    @GET("clientes/{id}")
    suspend fun getClientById(
        @Header("Authorization") token: String,
        @Path("id") clienteId: Int
    ): ClientsResponseItem

    @PUT("clientes/{id}")
    suspend fun updateCliente(
        @Header("Authorization") token: String,
        @Body request: NewClientBody,
        @Path("id") clienteId: Int
    ): UpdateClientResponse

    @DELETE("clientes/{id}")
    suspend fun deleteCliente(
        @Header("Authorization") token: String,
        @Path("id") clienteId: Int
    ): NewClientResponse
}