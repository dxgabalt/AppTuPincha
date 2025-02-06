package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MiPerfilViewModel(private val supabaseService: SupabaseService) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _mensajeEstado = MutableStateFlow("")
    val mensajeEstado: StateFlow<String> = _mensajeEstado

    init {
        cargarDatosUsuario()
    }

    private fun cargarDatosUsuario() {
        viewModelScope.launch {
            val user = supabaseService.obtenerUsuarioActual()
            _usuario.value = user
        }
    }

    fun actualizarNombre(nombre: String) {
        viewModelScope.launch {
            _usuario.value?.let { usuarioActual ->
                supabaseService.actualizarNombre(usuarioActual.id, nombre)
                _usuario.value = usuarioActual.copy(nombre = nombre)
                _mensajeEstado.value = "Nombre actualizado con éxito"
            }
        }
    }

    fun actualizarFotoPerfil(nuevaFoto: ByteArray) {
        viewModelScope.launch {
            val usuarioActual = _usuario.value
            if (usuarioActual != null) {
                val nuevaFotoUrl = supabaseService.subirFotoPerfil(usuarioActual.id, nuevaFoto)
                _usuario.value = usuarioActual.copy(fotoPerfilUrl = nuevaFotoUrl)
                _mensajeEstado.value = "Foto de perfil actualizada"
            }
        }
    }
}
