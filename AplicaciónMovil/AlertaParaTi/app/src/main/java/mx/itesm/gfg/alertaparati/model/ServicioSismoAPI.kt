package mx.itesm.gfg.alertaparati.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioSismoAPI {
    /**
     * Descargar datos globales. No lo vamos a usar*/
    /**
     * Descargar datos accidentes
     * v3/covid-19/countries <- es el servicio (parte de la url) */

    @GET("recuperarxsismo.php")
    fun descargarDatosSismos(): Call<List<Sismo>>

}