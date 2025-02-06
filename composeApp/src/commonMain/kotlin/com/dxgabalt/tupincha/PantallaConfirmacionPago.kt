import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.dxgabalt.tupincha.model.MetodoPago


class PantallaConfirmacionPago(
    private val idMetodo: Int,
    private val metodosDisponibles: List<MetodoPago>,
    private val onConfirmarPago: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val metodoPagoSeleccionado = metodosDisponibles.find { it.id == idMetodo }

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
                    text = "Confirmar Pago",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                metodoPagoSeleccionado?.let { metodo ->
                    // Mostrar el método de pago seleccionado
                    Text(
                        text = "Método de Pago: ${metodo.nombre}",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción específica del método de pago
                    Text(
                        text = obtenerDescripcionMetodoPago(metodo.nombre),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para confirmar el pago
                    Button(
                        onClick = onConfirmarPago,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF0314))
                    ) {
                        Text("Confirmar Pago", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                } ?: run {
                    CircularProgressIndicator()
                }
            }
        }
    }

    // Función para mostrar la descripción del método de pago
    private fun obtenerDescripcionMetodoPago(metodo: String): String {
        return when (metodo) {
            "Efectivo" -> "Pague directamente en el lugar del servicio."
            "Transferencia Bancaria" -> "Por favor, transfiera el monto al número de cuenta proporcionado."
            "Zelle" -> "Ingrese los detalles de Zelle proporcionados por el proveedor."
            "Cash App" -> "Utilice el ID de Cash App del proveedor para realizar el pago."
            else -> "Información adicional no disponible."
        }
    }
}

class PantallaPagoExitoso : Screen {
    @Composable
    override fun Content() {
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
                    text = "¡Pago Exitoso!",
                    fontSize = 24.sp,
                    color = Color(0xFF003366),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Su pago ha sido procesado correctamente.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
