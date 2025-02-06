package com.dxgabalt.tupincha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class ModalBuscarServicio : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

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
                    text = "¿Qué servicio buscas?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),  // Azul
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para búsqueda
                var busqueda by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar servicio", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de búsqueda
                Button(
                    onClick = {
                        if (busqueda.isNotEmpty()) {
                            navigator?.push(PantallaResultadosBusqueda(busqueda))
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314)),  // Rojo
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Buscar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
