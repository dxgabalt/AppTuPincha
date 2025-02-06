package com.dxgabalt.tupincha.mobile

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
import com.dxgabalt.tupincha.viewmodel.HistorialUsuarioViewModel
import com.dxgabalt.tupincha.model.HistorialItem
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


class PantallaHistorialUsuario(
    private val userId: String,
    private val onVerDetalle: (Int) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = HistorialUsuarioViewModel()
        val historial: List<HistorialItem> by viewModel.historial.collectAsState()
        LaunchedEffect(userId) {
            viewModel.cargarHistorialUsuario(userId)
        }

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Historial de Servicios",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(historial) { item ->
                        HistorialItemCard(item, onVerDetalle)
                    }
                }
            }
        }
    }

    @Composable
    fun HistorialItemCard(
        item: HistorialItem,
        onVerDetalle: (Int) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onVerDetalle(item.id) }
                .padding(8.dp),
            backgroundColor = Color(0xFFF2F2F2)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                // Imagen del proveedor utilizando Kamel
                KamelImage(
                    resource = asyncPainterResource(item.fotoProveedor),
                    contentDescription = "Foto del proveedor",
                    onLoading = { CircularProgressIndicator() },  // Indicador de carga
                    onFailure = { Text("Error al cargar imagen", color = Color.Red) },
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = item.proveedor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Servicio: ${item.servicio}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Fecha: ${item.fecha}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Estado: ${item.estado}",
                        fontSize = 14.sp,
                        color = when (item.estado) {
                            "completado" -> Color.Green
                            "pendiente" -> Color(0xFFFFA500)
                            else -> Color.Red
                        }
                    )
                }
            }
        }
    }
}
