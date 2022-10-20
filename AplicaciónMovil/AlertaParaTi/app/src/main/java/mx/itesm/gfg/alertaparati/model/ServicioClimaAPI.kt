package mx.itesm.gfg.alertaparati.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioClimaAPI {
    @GET("recuperarClima.php")
    fun descargarDatosClima(): Call<List<Clima>>
    @GET("recuperarClimaHora.php")
    fun descargarDatosClimaActual(): Call<List<ClimaActual>>
}