package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.model.MetodoPago
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfirmacionPagoViewModel : ViewModel() {

    private val _metodoPagoSeleccionado = MutableStateFlow<MetodoPago?>(null)
    val metodoPagoSeleccionado: StateFlow<MetodoPago?> = _metodoPagoSeleccionado

    fun cargarMetodoPago(metodoPago: MetodoPago) {
        viewModelScope.launch {
            _metodoPagoSeleccionado.value = metodoPago
        }
    }
}
