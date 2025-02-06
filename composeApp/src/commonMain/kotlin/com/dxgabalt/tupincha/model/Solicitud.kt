package com.dxgabalt.tupincha.model

data class Solicitud ( val provider_id: Int,
    val service_id: Int,
    val service_date: String,
    val images: String,
    val status: String)


data class DetalleSolicitudData(
    val id: Int,
    val requestDescription: String,
    val serviceDate: String,
    val status: String,
    val images: String?,
    val servicioCategoria: String?,
    val proveedorNombre: String?,
    val proveedorFoto: String?
)
