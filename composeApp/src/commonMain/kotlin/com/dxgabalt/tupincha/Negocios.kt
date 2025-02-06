package com.dxgabalt.tupincha

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dxgabalt.tupincha.model.Freelancer

class NegociosScreen(private val categoria: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        // Lista de freelancers ficticios
        val freelancersFicticios = listOf(
            Freelancer(1, "Carlos Pérez", "Electricista", 500.0f, "Disponible", "https://via.placeholder.com/60"),
            Freelancer(2, "Ana Gómez", "Plomero", 350.0f, "Ocupado", "https://via.placeholder.com/60"),
            Freelancer(3, "Juan López", "Carpintero", 600.0f, "Disponible", "https://via.placeholder.com/60"),
            Freelancer(4, "María Rodríguez", "Pintor", 400.0f, "Disponible", "https://via.placeholder.com/60"),
            Freelancer(5, "Pedro Díaz", "Constructor", 800.0f, "Ocupado", "https://via.placeholder.com/60")
        )

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Freelancers en $categoria",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(freelancersFicticios) { freelancer ->
                        FreelancerItem(freelancer) { id ->
                            println("Freelancer seleccionado: $id")  // Puedes reemplazarlo con la navegación
                            navigator?.push(ModalBuscarServicio())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FreelancerItem(
    freelancer: Freelancer,
    onFreelancerSeleccionado: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onFreelancerSeleccionado(freelancer.id) }
            .padding(8.dp),
        backgroundColor = Color(0xFFF2F2F2)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Nombre del freelancer
            Column {
                Text(
                    text = freelancer.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Especialidad: ${freelancer.especialidad}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Precio: ${freelancer.precio} CUP",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Disponibilidad: ${freelancer.disponibilidad}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
