package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.HistorialItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialUsuarioViewModel() : ViewModel() {
    private val supabaseService: SupabaseService = SupabaseService
    private val _historial = MutableStateFlow<List<HistorialItem>>(emptyList())
    val historial: StateFlow<List<HistorialItem>> = _historial

    fun cargarHistorialUsuario(userId: String) {
        viewModelScope.launch {
            val resultado = supabaseService.obtenerHistorialUsuario(userId)
            _historial.value = resultado
        }
    }
}
