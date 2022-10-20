package mx.itesm.gfg.alertaparati.model

import retrofit2.Call
import retrofit2.http.GET

interface ServicioNoticiaAPI
{
    /**
     * Descargar datos globales. No lo vamos a usar*/
    @GET("v3/covid-19/all")
    fun descargarDatosGlobales(): Call<String>
    /**
     * Descargar datos noticias
     * v3/covid-19/countries <- es el servicio (parte de la url) */
    @GET("v3/covid-19/countries")
    fun descargarDatosNoticias(): Call<List<Noticia>>
}