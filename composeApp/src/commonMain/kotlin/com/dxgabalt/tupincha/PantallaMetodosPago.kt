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
import com.dxgabalt.tupincha.model.MetodoPago
import com.dxgabalt.tupincha.viewmodel.MetodosPagoViewModel

class PantallaMetodosPago(
    private val onMetodoSeleccionado: (MetodoPago) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = MetodosPagoViewModel()
        val metodosPago by viewModel.metodosPago.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.cargarMetodosPago()
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
                    text = "Selecciona tu método de pago",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                 LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(metodosPago) { metodo ->
                        MetodoPagoItem(metodo, onMetodoSeleccionado)
                    }
                }
            }
        }
    }

    @Composable
    fun MetodoPagoItem(
        metodoPago: MetodoPago,
        onMetodoSeleccionado: (MetodoPago) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMetodoSeleccionado(metodoPago) }
                .padding(8.dp),
            backgroundColor = Color(0xFFF2F2F2)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = metodoPago.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
