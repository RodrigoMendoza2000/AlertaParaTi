package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tomtom.sdk.common.location.GeoCoordinate
import com.tomtom.sdk.maps.display.MapOptions
import com.tomtom.sdk.maps.display.TomTomMap
import com.tomtom.sdk.maps.display.camera.CameraOptions
import com.tomtom.sdk.maps.display.image.ImageFactory
import com.tomtom.sdk.maps.display.marker.MarkerOptions
import com.tomtom.sdk.maps.display.ui.MapFragment
import mx.itesm.gfg.alertaparati.R

class FragmentMapaSismo : Fragment() {

    // Argumentos fragmento
    val args: FragmentMapaSismoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa_sismo, container, false)
    }

    override fun onStart() {
        super.onStart()
        crearMapa()
    }

    private fun crearMapa() {
        val mapOptions = MapOptions(mapKey = "vn3eTDzoqirsGebNoHHfYRPXqBvOEWsl")
        val mapFragment = MapFragment.newInstance(mapOptions)
        parentFragmentManager.beginTransaction()
            .replace(R.id.map_container_sismo, mapFragment)
            .commit()

        mapFragment.getMapAsync{ tomTomMap: TomTomMap ->
            val sismo = GeoCoordinate(args.latitud.toDouble(), args.longitud.toDouble())
            val cameraOptions = CameraOptions(
                position = sismo,
                zoom = 4.0,
                tilt = 0.0,
                rotation = 0.0
            )
            tomTomMap.moveCamera(cameraOptions)

            val markerOptions = MarkerOptions(
                coordinate = sismo,
                pinImage = ImageFactory.fromResource(R.drawable.bomba_tres)
            )
            tomTomMap.addMarker(markerOptions)
        }
    }
}