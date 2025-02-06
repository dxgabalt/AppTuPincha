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
import com.dxgabalt.tupincha.viewmodel.DetalleSolicitudViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.*
import io.ktor.client.engine.cio.*

class PantallaDetalleSolicitud(private val solicitudId: Int) : Screen {

    @Composable
    override fun Content() {
        val viewModel = DetalleSolicitudViewModel()
        val detalleSolicitud by viewModel.detalleSolicitud.collectAsState()

        LaunchedEffect(solicitudId) {
            viewModel.cargarDetalleSolicitud(solicitudId)
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
                Text(
                    text = "Detalle del Servicio",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                detalleSolicitud?.let { detalle ->
                    // Imagen del proveedor usando Kamel
                    KamelImage(
                        resource = asyncPainterResource(detalle.fotoProveedor),
                        contentDescription = "Foto del proveedor",
                        onLoading = { CircularProgressIndicator() },  // Indicador mientras carga
                        onFailure = { Text("Error al cargar imagen", color = Color.Red) },
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del proveedor y servicio
                    Text("Proveedor: ${detalle.proveedor}", fontSize = 16.sp, color = Color.Gray)
                    Text("Servicio: ${detalle.servicio}", fontSize = 16.sp, color = Color.Gray)
                    Text("Fecha: ${detalle.fecha}", fontSize = 16.sp, color = Color.Gray)
                    Text(
                        text = "Estado: ${detalle.estado}",
                        fontSize = 16.sp,
                        color = when (detalle.estado) {
                            "completado" -> Color.Green
                            "pendiente" -> Color(0xFFFFA500)
                            else -> Color.Red
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción del servicio
                    Text("Descripción del servicio:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(detalle.descripcion, fontSize = 16.sp, color = Color.Black)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Imágenes relacionadas con el servicio (si están disponibles)
                    if (!detalle.imagenesUrl.isNullOrEmpty()) {
                        Text("Imágenes del servicio:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        KamelImage(
                            resource = asyncPainterResource(detalle.imagenesUrl),
                            contentDescription = "Imagen del servicio",
                            onLoading = { CircularProgressIndicator() },
                            onFailure = { Text("Error al cargar imagen", color = Color.Red) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                } ?: run {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
