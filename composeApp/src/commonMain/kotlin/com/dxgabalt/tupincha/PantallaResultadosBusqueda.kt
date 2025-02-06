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
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dxgabalt.tupincha.mobile.PantallaDetallesProveedor

class PantallaResultadosBusqueda(private val servicioBuscado: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Resultados para: \"$servicioBuscado\"",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366)  // Azul
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Lista simulada de resultados de búsqueda
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) { index ->  // Aquí cargarías resultados reales
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator?.push(PantallaDetallesProveedor(index,{}))
                                },
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Proveedor $index",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Especialidad: Servicio de ejemplo",
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
