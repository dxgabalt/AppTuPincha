package com.dxgabalt.tupincha

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
class Categoria:Screen {
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
                // Header de bienvenida
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿Qué ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFE758), shape = RoundedCornerShape(5.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "necesitas?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sección de "Tendencia"
                Text(
                    text = "Tendencia",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )

                // Lista horizontal de categorías destacadas
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    items(5) { // Aquí cargarías los datos reales
                        CategoriaTopItem(
                            nombre = "Categoría Top",
                            contador = "120",
                            colorFondo = Color(0xFFB0E57C), // Color de ejemplo, puede ser dinámico
                            foto = 1,  // Imagen de ejemplo
                            onCategoriaClick = { /* Acción al seleccionar */ }
                        )
                    }
                }

                // Sección de "Categorías"
                Text(
                    text = "Categorías",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )

                // Lista vertical de categorías normales
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    items(10) { // Aquí cargarías los datos reales
                        CategoriaItem(
                            nombre = "Categoría Normal",
                            contador = "50",
                            colorFondo = Color.White,
                            foto = 1,
                            onCategoriaClick = { /* Acción al seleccionar */ }
                        )
                    }
                }
            }
        }
    }
    @Composable
    fun CategoriaTopItem(
        nombre: String,
        contador: String,
        colorFondo: Color,
        foto: Int,
        onCategoriaClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(width = 150.dp, height = 200.dp)
                .clickable { onCategoriaClick() },
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorFondo
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                /* Image(
                painter = painterResource(id = foto),
                contentDescription = "Imagen de categoría",
                modifier = Modifier.size(100.dp)
                )*/
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /* Image(
                    painter = painterResource(id = R.drawable.ic_dispon),
                    contentDescription = "Icono contador",
                    modifier = Modifier.size(16.dp)
                    )*/
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = contador,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun CategoriaItem(
        nombre: String,
        contador: String,
        colorFondo: Color,
        foto: Int,
        onCategoriaClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clickable { onCategoriaClick() },
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorFondo
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                /*Image(
                    painter = painterResource(id = foto),
                    contentDescription = "Imagen de categoría",
                    modifier = Modifier.size(80.dp)
                )*/
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                       /* Image(
                            painter = painterResource(id = R.drawable.ic_dispon),
                            contentDescription = "Icono contador",
                            modifier = Modifier.size(16.dp)
                        )*/
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = contador,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }
}