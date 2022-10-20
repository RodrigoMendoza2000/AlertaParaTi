package mx.itesm.gfg.alertaparati.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MandarDatosAppAPI {
    @GET("insertarSesiones.php")
    fun mandarDatosSesion(
        @Query("fecha_inicio") fecha_inico: String,
        @Query("hora_inicio") hora_inicio: String,
        @Query("fecha_fin") fecha_fin: String,
        @Query("hora_fin") hora_fin: String,): Call<String>

    @GET("insertarPaginas.php")
    fun mandarDatosFragmentos(
        @Query("pagina") pagina: String,
        @Query("fecha") fecha: String,
        @Query("hora") hora: String): Call<String>
}