package com.dxgabalt.tupincha.model

data class DetalleSolicitud(
    val id: Int,
    val proveedor: String,
    val servicio: String,
    val descripcion: String,
    val fecha: String,
    val estado: String,
    val imagenesUrl: String?,
    val fotoProveedor: String
)
