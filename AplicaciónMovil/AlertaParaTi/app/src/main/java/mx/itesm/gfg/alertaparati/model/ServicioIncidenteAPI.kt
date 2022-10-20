package mx.itesm.gfg.alertaparati.model

import com.tomtom.sdk.common.location.GeoCoordinate
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface ServicioIncidenteAPI {
    /**
     * Descargar datos globales. No lo vamos a usar*/
    /**
     * Descargar datos accidente
     * v3/covid-19/countries <- es el servicio (parte de la url) */

    @GET("recuperarIncidentes.php")
    fun descargarDatosIncidentes(
        @Query("fecha_inicial") fecha_inical: String,
        @Query("fecha_termino") fecha_termino: String): Call<List<Incidente>>

    @GET("recuperarIncidentesCoordenadas.php")
    fun descargarCoordenadasIncidente(
        @Query("id_incidente") id_incidente: String): Call<List<Coordenada>>
}