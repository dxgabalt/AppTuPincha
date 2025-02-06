package com.dxgabalt.tupincha

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class RegistroScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            backgroundColor = Color.White  // Fondo blanco
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Registro",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),  // Azul
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                var nombre by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Acción de registro */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314)),  // Rojo
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Registrarse", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}