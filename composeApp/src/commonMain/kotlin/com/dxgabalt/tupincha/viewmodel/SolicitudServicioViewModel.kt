package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SolicitudServicioViewModel() : ViewModel() {
    private val supabaseService: SupabaseService = SupabaseService
    private val _mensajeEstado = MutableStateFlow("")
    val mensajeEstado: StateFlow<String> = _mensajeEstado

    fun enviarSolicitud(
        providerId: Int,
        serviceId: Int,
        descripcion: String,
        fechaServicio: String,
        imagenesUrl: String?
    ) {
        viewModelScope.launch {
            val exito = supabaseService.crearSolicitudDeServicio(providerId, serviceId, descripcion, fechaServicio, imagenesUrl)
            if (exito) {
                _mensajeEstado.value = "Solicitud enviada con éxito"
            } else {
                _mensajeEstado.value = "Error al enviar la solicitud"
            }
        }
    }
}
