package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.DetalleSolicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetalleSolicitudViewModel() : ViewModel() {
    private val supabaseService: SupabaseService = SupabaseService
    private val _detalleSolicitud = MutableStateFlow<DetalleSolicitud?>(null)
    val detalleSolicitud: StateFlow<DetalleSolicitud?> = _detalleSolicitud

    fun cargarDetalleSolicitud(solicitudId: Int) {
        viewModelScope.launch {
            val detalle = supabaseService.obtenerDetalleSolicitud(solicitudId)
            _detalleSolicitud.value = detalle
        }
    }
}
