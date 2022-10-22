package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mx.itesm.gfg.alertaparati.databinding.FragmentAlertasBinding

class AlertasFragment : Fragment() {

    private lateinit var binding : FragmentAlertasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertasBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnAlertaIncendios.setOnClickListener {
            val accion = AlertasFragmentDirections.actionAlertasFragmentToIncendios()
            findNavController().navigate(accion)
        }


    }
}