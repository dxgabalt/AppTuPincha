package com.dxgabalt.tupincha.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.dxgabalt.tupincha.viewmodel.DetallesProveedorViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


class PantallaDetallesProveedor(
    private val providerId: Int,
    private val onSolicitarServicio: (Int) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = DetallesProveedorViewModel()
        val freelancer by viewModel.freelancer.collectAsState()

        LaunchedEffect(providerId) {
            viewModel.cargarDetallesProveedor(providerId)
        }

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                freelancer?.let { freelancer ->
                    // Nombre del freelancer
                    Text(
                        text = freelancer.nombre,
                        fontSize = 24.sp,
                        color = Color(0xFF003366),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagen del freelancer usando Kamel
                    KamelImage(
                        resource = asyncPainterResource(freelancer.fotoPerfilUrl),
                        contentDescription = "Foto del freelancer",
                        onLoading = { CircularProgressIndicator() },  // Indicador de carga
                        onFailure = { Text("Error al cargar imagen", color = Color.Red) },
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información adicional del freelancer
                    Text("Especialidad: ${freelancer.especialidad}", fontSize = 16.sp, color = Color.Gray)
                    Text("Precio: ${freelancer.precio} CUP", fontSize = 16.sp, color = Color.Gray)
                    Text("Disponibilidad: ${freelancer.disponibilidad}", fontSize = 16.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón para solicitar servicio
                    Button(
                        onClick = { onSolicitarServicio(providerId) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                    ) {
                        Text("Solicitar servicio", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } ?: run {
                    CircularProgressIndicator()  // Mostrar indicador de carga si no hay datos
                }
            }
        }
    }
}
