package com.dxgabalt.tupincha.model

data class HistorialItem(
    val id: Int,
    val proveedor: String,
    val servicio: String,
    val fecha: String,
    val estado: String,
    val fotoProveedor: String
)
