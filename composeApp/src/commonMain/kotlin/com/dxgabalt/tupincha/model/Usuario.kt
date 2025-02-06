package com.dxgabalt.tupincha.model

data class Usuario(
    val id: String,
    val nombre: String,
    val correo: String,
    val fotoPerfilUrl: String
)
data class HistorialItemData(
    val id: Int,
    val serviceDate: String,
    val status: String,
    val servicioCategoria: String?,
    val proveedorNombre: String?,
    val proveedorFoto: String?
)

