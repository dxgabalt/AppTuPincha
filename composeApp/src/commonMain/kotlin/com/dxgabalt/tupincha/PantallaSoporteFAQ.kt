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
import com.dxgabalt.tupincha.model.FaqItem
import com.dxgabalt.tupincha.viewmodel.SoporteViewModel

class PantallaSoporteFAQ : Screen {

    @Composable
    override fun Content() {
        val viewModel = SoporteViewModel()
        val faqs by viewModel.faqs.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.cargarFaqs()
        }

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
                    text = "Soporte y FAQ",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de preguntas frecuentes
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(faqs) { faq ->
                        FaqItemCard(faq)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para abrir WhatsApp (multiplataforma)
                Button(
                    onClick = {
                        abrirWhatsApp("+12762763416", "Hola, necesito ayuda con la aplicación Tu Pincha.")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                ) {
                    Text("Contactar por WhatsApp", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    @Composable
    fun FaqItemCard(faq: FaqItem) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF2F2F2)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = faq.pregunta,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.respuesta,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun abrirWhatsApp(numero: String, mensaje: String) {
    //val url = "https://wa.me/$numero?text=${Uri.encode(mensaje)}"
    //openUrl(url)
}

fun openUrl(url: String) {
    // Esta función puede ser implementada utilizando multiplatform libraries como Ktor o Kamel para abrir links.
    println("Abrir URL: $url")  // Aquí puedes integrar tu lógica multiplataforma
}
