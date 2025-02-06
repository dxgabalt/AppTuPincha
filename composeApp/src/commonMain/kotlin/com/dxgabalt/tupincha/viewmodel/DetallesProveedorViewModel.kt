package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.Freelancer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetallesProveedorViewModel() : ViewModel() {
    private val supabaseService: SupabaseService = SupabaseService
    private val _freelancer = MutableStateFlow<Freelancer?>(null)
    val freelancer: StateFlow<Freelancer?> = _freelancer

    fun cargarDetallesProveedor(providerId: Int) {
        viewModelScope.launch {
            val detalles = supabaseService.obtenerDetallesProveedor(providerId)
            _freelancer.value = detalles
        }
    }
}
