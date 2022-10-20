package mx.itesm.gfg.alertaparati.model

import retrofit2.Call
import retrofit2.http.GET

interface ServicioClimaActualAPI {
    @GET("recuperarClimaHora.php")
    fun descargarDatosClimaActual(): Call<List<ClimaActual>>






















































}