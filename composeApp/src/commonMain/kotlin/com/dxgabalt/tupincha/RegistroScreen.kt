package com.dxgabalt.tupincha

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import com.dxgabalt.tupincha.data.SupabaseService

class RegistroScreen : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        var nombre by remember { mutableStateOf("") }
        var correo by remember { mutableStateOf("") }
        var contrasena by remember { mutableStateOf("") }
        var telefono by remember { mutableStateOf("") }
        var esProveedor by remember { mutableStateOf(false) }
        var especialidad by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }

        var mensajeEstado by remember { mutableStateOf("") }

        Scaffold(
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Registro de Cuenta", fontSize = 24.sp, color = Color(0xFF003366))

                Spacer(modifier = Modifier.height(16.dp))

                                // Campos básicos del formulario
                                OutlinedTextField(
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    label = { Text("Nombre Completo") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = correo,
                                    onValueChange = { correo = it },
                                    label = { Text("Correo Electrónico") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = contrasena,
                                    onValueChange = { contrasena = it },
                                    label = { Text("Contraseña") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = telefono,
                                    onValueChange = { telefono = it },
                                    label = { Text("Teléfono") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Switch para elegir si es proveedor o cliente
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("¿Te registras como proveedor de servicios?", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Switch(checked = esProveedor, onCheckedChange = { esProveedor = it })
                                }

                                // Campos adicionales si es proveedor
                                if (esProveedor) {
                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        value = especialidad,
                                        onValueChange = { especialidad = it },
                                        label = { Text("Especialidad (Ej: Fontanería, Electricidad)") },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        value = descripcion,
                                        onValueChange = { descripcion = it },
                                        label = { Text("Descripción del servicio") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Botón de registro
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            val resultado = registrarUsuario(
                                                nombre = nombre,
                                                correo = correo,
                                                contrasena = contrasena,
                                                telefono = telefono,
                                                esProveedor = esProveedor,
                                                especialidad = especialidad,
                                                descripcion = descripcion
                                            )
                                            mensajeEstado = resultado
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                                ) {
                                    Text("Registrarse", color = Color.White)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Mensaje de estado
                                if (mensajeEstado.isNotEmpty()) {
                                    Text(mensajeEstado, color = if (mensajeEstado.contains("éxito")) Color.Green else Color.Red)
                                }
                            }
                        }
                    }

                    // Función para registrar el usuario en Supabase
                    private suspend fun registrarUsuario(
                        nombre: String,
                        correo: String,
                        contrasena: String,
                        telefono: String,
                        esProveedor: Boolean,
                        especialidad: String,
                        descripcion: String
                    ): String {
                        return try {
                            // Crear el usuario en Supabase Auth
                            val userId = SupabaseService.crearUsuarioAuth(correo, contrasena)

                            // Guardar el perfil en la tabla "profile"
                            SupabaseService.guardarPerfil(
                                userId = userId,
                                nombre = nombre,
                                telefono = telefono
                            )

                            // Si es proveedor, guardar también en "providers"
                            if (esProveedor) {
                                SupabaseService.guardarProveedor(
                                    userId = userId,
                                    especialidad = especialidad,
                                    descripcion = descripcion
                                )
                            }

                            "Registro completado con éxito."
                        } catch (e: Exception) {
                            "Error durante el registro: ${e.message}"
                        }
                    }
                }
