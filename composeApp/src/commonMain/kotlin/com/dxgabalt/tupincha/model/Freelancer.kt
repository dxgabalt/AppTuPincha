package com.dxgabalt.tupincha.model

data class Freelancer(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val precio: Float,
    val disponibilidad: String,
    val fotoPerfilUrl: String
)
data class ProveedorConRelaciones(
    val id: Int,
    val speciality: String?,
    val profile: Usuario,
    val providerServices: ProviderService,
    val services: Service
)
data class ProviderService(
    val price: Float?,
    val availability: String?
)

data class Service(
    val category: String
)