package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.itesm.gfg.alertaparati.databinding.FragmentHomeBinding
import mx.itesm.gfg.alertaparati.ViewModel.MandaDatosBD

class HomeFragment : Fragment() {

    // Clase para mandar datos a la base de datos
    private var mandaDatosBD = MandaDatosBD()

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    override fun onStart() {
        super.onStart()
        mandaDatosBD.mandarDatosFragmento("Home")
    }

    private fun registrarEventos() {

        binding.btnImgClima.setOnClickListener {
            mandaDatosBD.mandarDatosFragmento("Clima")
            val accion = HomeFragmentDirections.actionNavigationHomeToFragmentClima()
            findNavController().navigate(accion)
        }
        binding.btnAlertas.setOnClickListener {
            mandaDatosBD.mandarDatosFragmento("Alarmas")
            val accion = HomeFragmentDirections.actionNavigationHomeToTerremoto()
            findNavController().navigate(accion)
        }
        binding.btnEstaciones.setOnClickListener {
            mandaDatosBD.mandarDatosFragmento("Estaciones")
            val accion = HomeFragmentDirections.actionNavigationHomeToEstacionesPolicias()
            findNavController().navigate(accion)
        }
        binding.btnTrafico.setOnClickListener {
            mandaDatosBD.mandarDatosFragmento("Tráfico")
            val accion = HomeFragmentDirections.actionNavigationHomeToTrafico()
            findNavController().navigate(accion)
        }
        binding.fab.setOnClickListener {
            val accion = HomeFragmentDirections.actionNavigationHomeToFragmentCreditos()
            findNavController().navigate(accion)
        }

    }

}