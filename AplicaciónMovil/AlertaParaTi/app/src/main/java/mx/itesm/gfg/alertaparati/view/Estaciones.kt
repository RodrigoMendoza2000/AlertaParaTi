package mx.itesm.gfg.alertaparati

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import mx.itesm.gfg.alertaparati.databinding.FragmentEstacionesPoliciasBinding

class EstacionesPolicias : Fragment() {

    private lateinit var binding: FragmentEstacionesPoliciasBinding
    // Código para solicitar permiso de usar la ubicación
    private val CODIGO_PERMISO_GPS = 200

    private var posicionActual: Location? = null

    // Cliente proveedor de ubicación
    private lateinit var clienteLocalizacion: FusedLocationProviderClient

    // Callback para manejar las actualizaciones de ubicación
    private lateinit var locationCallback: LocationCallback

    // Para saber si las actualizaciones están activas entre corridas de la app
    private var actualizandoPosicion: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEstacionesPoliciasBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        recuperarActualizandoPosicion(savedInstanceState)

        // Handler de actualizaciones. La función se ejecuta cada vez que hay una nueva posición
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val posicion = locationResult.locations.last()
                println("Nueva ubicación: $posicion")
                if (posicion != null) {
                    posicionActual = posicion
                    detenerActualizacionesPosicion()
                }
            }
        }
    }

    private fun registrarEventos() {
        binding.btnEstacionPolicias.setOnClickListener {
            mostrarMapa("Policias")
        }
        binding.btnEstacionBomberos.setOnClickListener {
            mostrarMapa("Bomberos")
        }
        binding.btnHospital.setOnClickListener {
            mostrarMapa("Hospital")
        }

    }
    private fun mostrarMapa(estaciones: String) {
        if(posicionActual!=null) {
            val uri = Uri.parse("geo:${posicionActual!!.latitude}, ${posicionActual!!.longitude}?q=${estaciones}")
            val intMapa = Intent(Intent.ACTION_VIEW, uri)
            intMapa.setPackage("com.google.android.apps.maps")
            startActivity(intMapa)
        }
        else{
            println("Es null")
        }
    }


    private fun recuperarActualizandoPosicion(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        if (savedInstanceState.containsKey("ActualizandoPosicion")) {
            actualizandoPosicion = savedInstanceState.getBoolean("ActualizandoPosicion")
        }
    }

    private fun configurarGPS() {
        if (tienePermiso()) {
            iniciarActualizacionesPosicion()
        } else {
            solicitarPermisos()
        }
        leerUltimaUbicacion()
    }

    private fun leerUltimaUbicacion() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        clienteLocalizacion.lastLocation
            .addOnSuccessListener { location: Location? ->
                println("Ultima ubicación: $location")
            }
    }

    private fun solicitarPermisos() {
        val requiereJustificacion = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (requiereJustificacion) {
            mostrarDialogo()
        } else { // Pedir el permiso directo
            pedirPermisoUbicacion()
        }
    }


    // Resultado del permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CODIGO_PERMISO_GPS) {
            if (grantResults.isEmpty()) {
            } else if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                iniciarActualizacionesPosicion()
            } else {
                // Permiso negado
                val dialogo = AlertDialog.Builder(requireContext())
                dialogo.setMessage("Esta app requiere GPS, ¿Quieres habilitarlo manualmente?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
                    .setNeutralButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                        println("No hay forma de usar gps, cerrar la actividad")
                        //Deshabilitar funcionalidad
                    })
                    .setCancelable(false)
                dialogo.show()
            }
        }
    }

    private fun mostrarDialogo() {
        val dialogo = AlertDialog.Builder(requireContext())
            .setMessage("Necesitamos saber tu posición para generar alertas")
            .setPositiveButton("Aceptar") { dialog, which ->
                pedirPermisoUbicacion()
            }
            .setNeutralButton("Cancelar", null)
        dialogo.show()
    }

    private fun pedirPermisoUbicacion() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS
        )
    }

    @SuppressLint("MissingPermission")
    private fun iniciarActualizacionesPosicion() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 20000
            //fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        clienteLocalizacion.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        actualizandoPosicion = true
    }

    private fun tienePermiso(): Boolean {
        val estado = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return estado == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        if (actualizandoPosicion) {
            iniciarActualizacionesPosicion()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!this::clienteLocalizacion.isInitialized) {
            clienteLocalizacion = LocationServices.getFusedLocationProviderClient(requireContext())
            configurarGPS()
        }
    }

    override fun onPause() {
        super.onPause()
        detenerActualizacionesPosicion()
    }

    override fun onStop() {
        super.onStop()
        println("DETENIENDO")
    }

    private fun detenerActualizacionesPosicion() {
        println("Detiene actualizaciones")
        clienteLocalizacion.removeLocationUpdates(locationCallback)
        actualizandoPosicion = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putBoolean("ActualizandoPosicion", actualizandoPosicion)
        super.onSaveInstanceState(outState)
    }
}