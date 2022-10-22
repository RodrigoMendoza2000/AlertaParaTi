package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tomtom.sdk.common.location.GeoCoordinate
import com.tomtom.sdk.maps.display.MapOptions
import com.tomtom.sdk.maps.display.TomTomMap
import com.tomtom.sdk.maps.display.camera.CameraOptions
import com.tomtom.sdk.maps.display.common.Color
import com.tomtom.sdk.maps.display.common.WidthByZoom
import com.tomtom.sdk.maps.display.image.ImageFactory
import com.tomtom.sdk.maps.display.marker.MarkerOptions
import com.tomtom.sdk.maps.display.polyline.CapType
import com.tomtom.sdk.maps.display.polyline.PolylineOptions
import com.tomtom.sdk.maps.display.ui.MapFragment
import mx.itesm.gfg.alertaparati.R
import mx.itesm.gfg.alertaparati.ViewModel.ListaIncidentesVM


class FragmentMapaIncidente : Fragment() {

    //viewmodel
    private val viewmodel : ListaIncidentesVM by viewModels()

    // Argumentos fragmento
    val args: FragmentMapaIncidenteArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa_incidente, container, false)
    }

    override fun onStart() {
        super.onStart()
        configurarObservables()
        descargarCoordenadas()
    }

    private fun configurarObservables() {
        // pedirle a viewmodel que regrese la referencia de lista paises
        viewmodel.listaCoordenadasObs.observe(requireActivity()){ lista ->
            viewmodel.listaCoordenadasObs.value?.let { crearMapa(it) }
        }
    }

    fun descargarCoordenadas() {

        viewmodel.descargarCoordenadasIncidnte(args.idIncidente)
    }

    private fun crearMapa(coordenadas: List<GeoCoordinate>) {
        val mapOptions = MapOptions(mapKey = "vn3eTDzoqirsGebNoHHfYRPXqBvOEWsl")
        val mapFragment = MapFragment.newInstance(mapOptions)
        parentFragmentManager.beginTransaction()
            .replace(R.id.map_container_incidente, mapFragment)
            .commit()

        mapFragment.getMapAsync{ tomTomMap: TomTomMap ->
            val incidente: GeoCoordinate = coordenadas[0]
            val cameraOptions = CameraOptions(
                position = incidente,
                zoom = 16.0,
                tilt = 0.0,
                rotation = 90.0
            )
            tomTomMap.moveCamera(cameraOptions)

            val markerOptions = MarkerOptions(
                coordinate = incidente,
                pinImage = ImageFactory.fromResource(R.drawable.pin)
            )
            tomTomMap.addMarker(markerOptions)


            val lineColor = Color(args.color)
            val outlineColor = Color(args.color)
            val polylineOptions = PolylineOptions(
                coordenadas,
                lineColor = lineColor,
                lineWidths = listOf(WidthByZoom(8.0)),
                outlineColor = outlineColor,
                outlineWidths = listOf(WidthByZoom(1.0)),
                lineStartCapType = CapType.ROUND,
                lineEndCapType = CapType.ROUND
            )
            tomTomMap.addPolyline(polylineOptions)
        }
    }
}