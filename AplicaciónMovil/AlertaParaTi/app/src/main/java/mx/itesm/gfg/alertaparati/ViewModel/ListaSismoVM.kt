package mx.itesm.gfg.alertaparati.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.itesm.gfg.alertaparati.model.ServicioSismoAPI
import mx.itesm.gfg.alertaparati.model.Sismo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaSismoVM : ViewModel() {
    // Modelo
    private val retrofit by lazy {
        // El objeto retrofit para instanciar
        // el objeto que se conecta a la red y accede a los servicios definidos
        Retrofit.Builder()
            .baseUrl("https://alerta-para-ti.azurewebsites.net/") //url de la API sismos
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia que crea el objeto que realiza la descarga
    private val servicioSismoAPI by lazy {
        retrofit.create(ServicioSismoAPI::class.java)
    }

    //Observables
    val listaSismos = MutableLiveData<List<Sismo>>()

    // Interfaz del VM
    fun descargarDatoSismos() {
        val call = servicioSismoAPI.descargarDatosSismos()
        call.enqueue(object : Callback<List<Sismo>> {
            override fun onResponse(call: Call<List<Sismo>>, response: Response<List<Sismo>>) {
                if (response.isSuccessful) {
                    // Avisar a la vista (adaptador) que hay datos nuevos
                    listaSismos.value = response.body()
                } else {
                    println("Error en los datos: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Sismo>>, t: Throwable) {
                println("Error en la descarga: ${t.localizedMessage}")
            }
        })
    }
}

