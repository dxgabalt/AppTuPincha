package com.dxgabalt.tupincha.data

import com.dxgabalt.tupincha.model.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.decode
import io.github.jan.supabase.postgrest.execute
import io.github.jan.supabase.postgrest.filter.eq
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Returning
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object SupabaseService {

    private const val SUPABASE_URL = "https://idngwsekicptfluqumys.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

    // Inicialización del cliente
    private val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }

    suspend fun crearUsuarioAuth(correo: String, contrasena: String): String {
        val resultado = client.auth.signUpWith(Email) {
            email = correo
            password = contrasena
        }
        return resultado?.id ?: throw Exception("Error al crear el usuario.")
    }


    suspend fun guardarPerfil(userId: String, nombre: String, telefono: String) {

        val profile = Profile(user_id = userId, name = nombre,phone=telefono)
        val resultado = client.postgrest["profile"].insert(listOf(profile)).decodeList<Profile>()
        if (resultado.error != null) {
            throw Exception("Error al guardar el perfil: ${resultado.error.message}")
        }
    }

   suspend fun guardarProveedor(userId: String, especialidad: String, descripcion: String) {
    // Recupera el profile_id desde la tabla profile

           val profileResult = client.postgrest["profile"]
               .select(columns = Columns.list("id")) {
                   eq("user_id", userId)
               }.decodeSingle<Map<String, Any?>>()
       val proveedor = Proveedor(profile_id = profileId, speciality = especialidad,description=descripcion)
       val insertResult = client.postgrest["providers"].insert(listOf(proveedor)).decodeList<Proveedor>()

    if (profileResult.error != null || profileResult.data == null) {
        throw Exception("Error al recuperar el profile_id: ${profileResult.error?.message ?: "No se encontró el profile"}")
    }

    val profileId = (profileResult.data as Map<String, Any>)["id"] as? Int
        ?: throw Exception("Error al convertir el profile_id")

    // Crear el objeto del proveedor

       val proveedor = Proveedor(profile_id = profileId, speciality = especialidad,description=descripcion)
       val insertResult = client.postgrest["providers"].insert(listOf(proveedor)).decodeList<Proveedor>()
    if (insertResult.isEmpty()) {
        throw Exception("Error al guardar el proveedor: ${insertResult}")
    }
}



    suspend fun obtenerUsuarioActual(): Usuario? {
        return try {
            // Recuperar el usuario actual
            val user = client.auth.retrieveUserForCurrentSession(updateSession = true)

            if (user == null) {
                println("No hay sesión activa.")
                return null
            }

            // Consultar detalles del perfil en la tabla "profile" usando filter
            val perfilResponse = client.postgrest.from("profile").select {
                filter {
                    eq("user_id", user.id)
                }
            }.decodeSingleOrNull<Usuario>()
            // Verificar si se encontró el perfil
            if (perfilResponse == null) {
                println("No se encontró el perfil del usuario.")
                return null
            }

            // Mapear los datos al modelo "Usuario"
            Usuario(
                id = user.id,
                nombre = perfilResponse.nombre as String,
                correo = user.email as? String ?: "",
                fotoPerfilUrl = perfilResponse.fotoPerfilUrl as? String ?: ""
            )

        } catch (e: Exception) {
            println("Error al obtener el usuario actual: ${e.message}")
            null
        }
    }

    suspend fun actualizarNombre(idUsuario: String, nuevoNombre: String) {
        client.from("profile")
            .update(mapOf("name" to nuevoNombre))
            {
                filter {
                    eq("user_id", idUsuario)
                }
            }
    }

    suspend fun subirFotoPerfil(idUsuario: String, foto: ByteArray): String {
        val storage = client.storage.from("imagenes-perfil")
        val path = "$idUsuario.jpg"
        storage.upload(path, foto)
        return "https://idngwsekicptfluqumys.supabase.co/storage/v1/object/public/imagenes-perfil/$path"
    }

    // Función para autenticar usuarios
    suspend fun autenticarUsuario(correo: String, password: String): Boolean {
        try {
            client.auth.signInWith(Email) {
                email = correo
                password
            }
            return true
        } catch (e: Exception) {
            println("Error de autenticación: ${e.message}")
            return false
            
        }
    }

    // Obtener freelancers por categoría
    suspend fun obtenerFreelancersPorCategoria(categoria: String): List<Map<String, Any?>> =
        withContext(Dispatchers.IO) {
            val response = client.postgrest.from("providers")
                .select(columns = Columns.list("name", "especialidad")) {
                    filter {
                        eq("category", categoria)
                    }
                }

            return@withContext response.decodeList()
        }

    // Guardar una nueva solicitud de servicio
    suspend fun crearSolicitudDeServicio(
        providerId: Int,
        serviceId: Int,
        descripcion: String,
        fechaServicio: String,
        imagenesUrl: String?
    ): Boolean {
        try {
            val solicitud = Solicitud(
                provider_id = providerId,
                service_id = serviceId,
                service_date = fechaServicio,
                images = "",
                status = "Pendiente"
            )

            // Inserción en la tabla "requests"
            client.postgrest.from("requests").insert(solicitud)
            return false
        } catch (e: Exception) {
            println("Error al guardar solicitud: ${e.message}")
            return true
        }

    }

    suspend fun obtenerDetallesProveedor(providerId: Int): Freelancer? {
        return try {
            // Consulta a la tabla "providers"
            val providerData = client.postgrest.from("providers")
                .select(
                    columns = Columns.list(
                        "*",
                        "profile(name, profile_pic_url)",
                        " provider_services(price, availability)",
                        "services(category)"
                    )
                ) {
                    filter {
                        eq("id", providerId)
                    }
                }.decodeSingle<ProveedorConRelaciones>()

            // Mapear los datos al modelo "Freelancer"
            Freelancer(
                id = providerId,
                nombre = providerData.profile.nombre,
                especialidad = providerData.speciality ?: "No especificada",
                precio = providerData.providerServices.price ?: 0f,
                disponibilidad = providerData.providerServices.availability ?: "No disponible",
                fotoPerfilUrl = providerData.profile.fotoPerfilUrl ?: ""
            )
        } catch (e: Exception) {
            println("Error al obtener los detalles del proveedor: ${e.message}")
            null
        }
    }

    suspend fun obtenerHistorialUsuario(userId: String): List<HistorialItem> {
        return client.postgrest.from("requests").select(
            columns = Columns.list(
                "id",
                "service_date",
                "status",
                "services.category",
                "providers.profile.name",
                "providers.profile.profile_pic_url"
            )
        ) {
            filter {
                eq("user_id", userId)
            }
        }.decodeList<HistorialItemData>().map { data ->
            HistorialItem(
                id = data.id,
                proveedor = data.proveedorNombre ?: "Desconocido",
                servicio = data.servicioCategoria ?:"",
                fecha = data.serviceDate,
                estado = data.status,
                fotoProveedor = data.proveedorFoto ?: ""
            )
        }
    }
    suspend fun obtenerDetalleSolicitud(solicitudId: Int): DetalleSolicitud? {
        return client.postgrest.from("requests").select(
            columns = Columns.list(
                "id",
                "request_description",
                "service_date",
                "status",
                "images",
                "services.category",
                "providers.profile.name",
                "providers.profile.profile_pic_url"
            )
        ) {
            filter {
                eq("id", solicitudId)
            }
        }.decodeSingle<DetalleSolicitudData>()?.let { data ->
            DetalleSolicitud(
                id = data.id,
                proveedor = data.proveedorNombre ?: "Desconocido",
                servicio = data.servicioCategoria ?: "Sin categoría",
                descripcion = data.requestDescription,
                fecha = data.serviceDate,
                estado = data.status,
                imagenesUrl = data.images,
                fotoProveedor = data.proveedorFoto ?: ""
            )
        }
    }
    suspend fun obtenerMetodosPago(): List<MetodoPago> {
        return client.postgrest.from("transaction_types").select(
            columns = Columns.list("id", "name")
        ).decodeList<MetodoPago>()
    }
    suspend fun obtenerFaqs(): List<FaqItem> {
        return client.postgrest.from("faqs").select(
            columns = Columns.list("question", "answer")
        ).decodeList<FaqItem>()
    }
    
}