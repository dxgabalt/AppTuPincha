package com.dxgabalt.tupincha

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class PantallaHistorialProveedor : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Historial de servicios prestados",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366)  // Azul
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Lista simulada de historial del proveedor
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) { index ->  // Aquí cargarías datos reales
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* Acción para ver detalles del servicio */ },
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Servicio $index - Cliente $index",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Estado: Completado",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
