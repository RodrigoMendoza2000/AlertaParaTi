package mx.itesm.gfg.alertaparati.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomtom.sdk.common.location.GeoCoordinate
import mx.itesm.gfg.alertaparati.model.Coordenada
import mx.itesm.gfg.alertaparati.model.Incidente
import mx.itesm.gfg.alertaparati.model.ServicioIncidenteAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListaIncidentesVM : ViewModel() {
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
    private val servicioIncidenteAPI by lazy {
        retrofit.create(ServicioIncidenteAPI::class.java)
    }

    //Observables
    val listaIncidentes = MutableLiveData<List<Incidente>>()
    val listaCoordenadasObs = MutableLiveData<List<GeoCoordinate>>()

    // Variables
    var listaCoordenadas = mutableListOf<GeoCoordinate>()

    // Interfaz del VM
    fun descargarDatosIncidentes() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fecha = LocalDateTime.now().format(formatter)

        val call = servicioIncidenteAPI.descargarDatosIncidentes(fecha, fecha)
        call.enqueue(object : Callback<List<Incidente>> {
            override fun onResponse(
                call: Call<List<Incidente>>,
                response: Response<List<Incidente>>
            ) {
                if (response.isSuccessful) {
                    // Avisar a la vista (adaptador) que hay datos nuevos
                    listaIncidentes.value = response.body()

                } else {
                    println("Error en los datos de incidentes: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Incidente>>, t: Throwable) {
                println("Error en la descarga de incidentes: ${t.localizedMessage}")
            }
        })
    }

    fun descargarCoordenadasIncidnte(id_incidente: String) {
        val call = servicioIncidenteAPI.descargarCoordenadasIncidente(id_incidente)
        call.enqueue(object : Callback<List<Coordenada>> {
            override fun onResponse(
                call: Call<List<Coordenada>>,
                response: Response<List<Coordenada>>
            ) {
                if (response.isSuccessful) {
                    println("Coordenadas de incidente: ${response.body()}")
                    // Avisar a la vista que hay datos nuevos
                    toGeoCoordinates(response.body()!!)
                    listaCoordenadasObs.value = listaCoordenadas.toList()
                } else {
                    println("Error en los datos de coordenadas: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Coordenada>>, t: Throwable) {
                println("Error en la descarga de coordenadas: ${t.localizedMessage}")
            }
        })
    }

    fun toGeoCoordinates(coordenadas: List<Coordenada>) {
        for (coordenada in coordenadas) {
            val geo_coordinate = GeoCoordinate(coordenada.latitud, coordenada.longitud)
            listaCoordenadas.add(geo_coordinate)
        }
    }
}