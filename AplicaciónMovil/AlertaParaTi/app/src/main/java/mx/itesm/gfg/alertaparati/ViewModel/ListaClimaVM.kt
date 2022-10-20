package mx.itesm.gfg.alertaparati.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomtom.sdk.common.location.GeoCoordinate
import mx.itesm.gfg.alertaparati.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListaClimaVM : ViewModel() {
    // Modelo
    private val retrofit by lazy {
        // El objeto retrofit para instanciar
        // el objeto que se conecta a la red y accede a los servicios definidos
        Retrofit.Builder()
            .baseUrl("https://alerta-para-ti.azurewebsites.net/") //url de la API incidente
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia que crea el objeto que realiza la descarga
    private val servicioClimaAPI by lazy {
        retrofit.create(ServicioClimaAPI::class.java)
    }

    //Observables
    val listaClimas = MutableLiveData<List<Clima>>()
    val listaClimaActual = MutableLiveData<List<ClimaActual>>()


    // Interfaz del VM
    fun descargarDatoClima() {
        val call = servicioClimaAPI.descargarDatosClima()
        call.enqueue(object : Callback<List<Clima>> {
            override fun onResponse(call: Call<List<Clima>>, response: Response<List<Clima>>) {
                if (response.isSuccessful) {
                    // Avisar a la vista (adaptador) que hay datos nuevos
                    listaClimas.value = response.body()
                } else {
                    println("Error en los datos: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Clima>>, t: Throwable) {
                println("Error en la descarga: ${t.localizedMessage}")
            }
        })
    }

    fun descargarDatosClimaActual(){
        val call = servicioClimaAPI.descargarDatosClimaActual()
        call.enqueue(object : Callback<List<ClimaActual>> {
            override fun onResponse(call: Call<List<ClimaActual>>, response: Response<List<ClimaActual>>) {
                if (response.isSuccessful) {
                    // Avisar a la vista (adaptador) que hay datos nuevos
                    listaClimaActual.value = response.body()
                } else {
                    println("Error en los datos: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ClimaActual>>, t: Throwable) {
                println("Error en la descarga: ${t.localizedMessage}")
            }
        })
    }

}

