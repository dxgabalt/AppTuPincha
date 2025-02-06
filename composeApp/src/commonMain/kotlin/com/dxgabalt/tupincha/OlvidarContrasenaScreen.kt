package com.dxgabalt.tupincha
import androidx.compose.foundation.background
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

class OlvidarContrasenaScreen : Screen {
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
                    "Recuperar contraseña",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),  // Azul
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Acción para enviar enlace de recuperación */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314)),  // Rojo
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Enviar enlace", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
