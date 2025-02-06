package com.dxgabalt.tupincha.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
// import coil.compose.rememberImagePainter
import com.dxgabalt.tupincha.viewmodel.MiPerfilViewModel

@Composable
fun MiPerfilScreen(viewModel: MiPerfilViewModel) {
    val usuario by viewModel.usuario.collectAsState()
    val mensajeEstado by viewModel.mensajeEstado.collectAsState()

    Scaffold(
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Mi Perfil", fontSize = 24.sp, color = Color(0xFF003366))

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la imagen de perfil
            usuario?.fotoPerfilUrl?.let { fotoUrl ->
               /* Image(
                    painter = rememberImagePainter(fotoUrl),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(100.dp)
                ) */
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del usuario
            var nombre by remember { mutableStateOf(usuario?.nombre ?: "") }
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para guardar los cambios
            Button(
                onClick = { viewModel.actualizarNombre(nombre) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
            ) {
                Text("Guardar cambios", color = Color.White)
            }

            // Mensaje de estado
            if (mensajeEstado.isNotEmpty()) {
                Text(mensajeEstado, color = Color.Green, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}