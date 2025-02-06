package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.MetodoPago
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetodosPagoViewModel() : ViewModel() {
    private val supabaseService: SupabaseService= SupabaseService
    private val _metodosPago = MutableStateFlow<List<MetodoPago>>(emptyList())
    val metodosPago: StateFlow<List<MetodoPago>> = _metodosPago

    fun cargarMetodosPago() {
        viewModelScope.launch {
            val metodos = supabaseService.obtenerMetodosPago()
            _metodosPago.value = metodos
        }
    }
}
