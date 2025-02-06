package com.dxgabalt.tupincha.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.dxgabalt.tupincha.viewmodel.SolicitudServicioViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class PantallaSolicitudServicio(
    private val providerId: Int,
    private val serviceId: Int = 1  // Asignar un serviceId por defecto
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = SolicitudServicioViewModel()
        val mensajeEstado by viewModel.mensajeEstado.collectAsState()

        var descripcion by remember { mutableStateOf("") }
        var fechaServicio by remember { mutableStateOf("") }
        var imagenesUrl by remember { mutableStateOf<String?>(null) }

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Solicitud de servicio",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de descripción
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción del servicio") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFFF0314),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selección de fecha
                Button(
                    onClick = {
                        // Obtener la fecha actual usando kotlinx-datetime
                        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

                        // Formatear la fecha como "yyyy-MM-dd"
                        fechaServicio = "${currentDate.year}-${currentDate.monthNumber}-${currentDate.dayOfMonth}"
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                ) {
                    Text(
                        text = if (fechaServicio.isEmpty()) "Seleccionar fecha" else "Fecha: $fechaServicio",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo opcional para subir imágenes
                OutlinedTextField(
                    value = imagenesUrl ?: "",
                    onValueChange = { imagenesUrl = it },
                    label = { Text("URL de imagen (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Uri)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para enviar la solicitud
                Button(
                    onClick = {
                        viewModel.enviarSolicitud(providerId, serviceId, descripcion, fechaServicio, imagenesUrl)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                ) {
                    Text("Enviar solicitud", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de estado
                if (mensajeEstado.isNotEmpty()) {
                    Text(
                        mensajeEstado,
                        color = if (mensajeEstado.contains("éxito")) Color.Green else Color.Red
                    )
                }
            }
        }
    }
}
