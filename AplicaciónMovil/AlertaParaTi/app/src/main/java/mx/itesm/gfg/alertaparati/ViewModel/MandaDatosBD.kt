package mx.itesm.gfg.alertaparati.ViewModel

import mx.itesm.gfg.alertaparati.model.DatosSesion
import mx.itesm.gfg.alertaparati.model.MandarDatosAppAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MandaDatosBD {
    // Retrofit para mandar datos de la sesion
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://alerta-para-ti.azurewebsites.net/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
    // Instancia que crea el objeto que realiza la descarga
    private val mandarDatosAppAPI by lazy {
        retrofit.create(MandarDatosAppAPI::class.java)
    }

    fun mandarDatosSesion(sesion: DatosSesion) {
        val call = mandarDatosAppAPI.mandarDatosSesion(
            "2022-10-25",
            sesion.hora_inicio,
            "2022-10-25",
            sesion.hora_fin)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println(response.body())
                } else {
                    println("Error en los datos al mandar sesion: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error en mandar sesion: ${t.localizedMessage}")
            }
        })
    }

    fun mandarDatosFragmento(pagina: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHora = LocalDateTime.now().format(formatter).split(" ")

        val call = mandarDatosAppAPI.mandarDatosFragmentos(pagina, "2022-10-25", fechaHora[1])
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println(response.body())
                    println(pagina)
                    println(fechaHora[0])
                    println(fechaHora[1])
                } else {
                    println("Error en los datos al mandar fragmentos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error en mandar datos fragmentos: ${t.localizedMessage}")
            }
        })
    }


}