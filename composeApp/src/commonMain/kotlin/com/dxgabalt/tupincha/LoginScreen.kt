package com.dxgabalt.tupincha
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003366)),  // Fondo azul
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la app
                Text(
                    "Tu Pincha",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo", color = Color.LightGray) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        placeholderColor = Color.Gray,
                        focusedBorderColor = Color(0xFFFF0314),  // Rojo
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password Input
                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña", color = Color.LightGray) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        placeholderColor = Color.Gray,
                        focusedBorderColor = Color(0xFFFF0314),  // Rojo
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Login Button
                Button(
                    onClick = { navigator.push(NegociosScreen("construccion")) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314)),  // Rojo
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text("Iniciar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Create Account Button
                TextButton(onClick = { navigator.push(RegistroScreen()) }) {
                    Text("Crear cuenta", color = Color.White, fontWeight = FontWeight.Bold)
                }

                // Forgot Password Button
                TextButton(onClick = { navigator.push(OlvidarContrasenaScreen()) }) {
                    Text("¿Olvidaste tu contraseña?", color = Color(0xFFFF0314), fontWeight = FontWeight.Bold)
                }
            }

            // Footer
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text("Una app de ", color = Color.Gray)
                    Text("Software Nicaragua", color = Color(0xFFFFD340))
                }
            }
        }
    }
}