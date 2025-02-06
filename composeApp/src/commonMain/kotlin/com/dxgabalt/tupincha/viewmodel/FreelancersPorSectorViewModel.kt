package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.Freelancer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FreelancersPorSectorViewModel() : ViewModel() {
    private val supabaseService: SupabaseService= SupabaseService

     // Estado de los freelancers con diferentes casos de carga
    private val _estadoFreelancers = MutableStateFlow<FreelancerState>(FreelancerState.Loading)
    val estadoFreelancers: StateFlow<FreelancerState> = _estadoFreelancers

    fun cargarFreelancers(categoria: String) {
        viewModelScope.launch {
            try {
                _estadoFreelancers.value = FreelancerState.Loading

                // Consulta de Supabase
                val resultado = supabaseService.obtenerFreelancersPorCategoria(categoria)

                // Mapeo de los resultados a la clase Freelancer
                val freelancers = resultado.mapNotNull { map ->
                    mapToFreelancer(map)
                }

                if (freelancers.isNotEmpty()) {
                    _estadoFreelancers.value = FreelancerState.Success(freelancers)
                } else {
                    _estadoFreelancers.value = FreelancerState.Empty("No se encontraron freelancers en la categoría $categoria.")
                }
            } catch (e: Exception) {
                _estadoFreelancers.value = FreelancerState.Error("Ocurrió un error al cargar los freelancers: ${e.message}")
            }
        }
    }

    // Función auxiliar para mapear el resultado de Supabase a la clase Freelancer
    private fun mapToFreelancer(map: Map<String, Any?>): Freelancer? {
        return try {
            Freelancer(
                id = map["id"] as? Int ?: return null,
                nombre = map["name"] as? String ?: "Desconocido",
                especialidad = map["speciality"] as? String ?: "No especificada",
                precio = (map["price"] as? Double)?.toFloat() ?: 0f,
                disponibilidad = map["availability"] as? String ?: "No disponible",
                fotoPerfilUrl = map["profile_pic_url"] as? String ?: ""
            )
        } catch (e: Exception) {
            println("Error al mapear el freelancer: ${e.message}")
            null
        }
    }

    // Estados posibles
    sealed class FreelancerState {
        object Loading : FreelancerState()
        data class Success(val freelancers: List<Freelancer>) : FreelancerState()
        data class Empty(val mensaje: String) : FreelancerState()
        data class Error(val mensaje: String) : FreelancerState()
    }
}
