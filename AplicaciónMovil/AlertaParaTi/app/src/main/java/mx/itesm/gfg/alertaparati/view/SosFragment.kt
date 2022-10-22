package mx.itesm.gfg.alertaparati.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.itesm.gfg.alertaparati.databinding.FragmentSosBinding
import mx.itesm.gfg.alertaparati.ViewModel.MandaDatosBD

class SosFragment : Fragment() {

    // Clase para mandar datos a la base de datos
    private var mandaDatosBD = MandaDatosBD()

    // Binding
    private lateinit var binding: FragmentSosBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        binding = FragmentSosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    override fun onStart() {
        super.onStart()
        mandaDatosBD.mandarDatosFragmento("SOS")
    }


    private fun registrarEventos() {
        binding.btnNumPol.setOnClickListener {
            hacerLlamada(5511062163)
        }
        binding.btnNumBomb.setOnClickListener {
            hacerLlamada(5536221004)
        }
        binding.btnNumParamedicos.setOnClickListener {
            hacerLlamada(5558222547)
        }
        }

    private fun hacerLlamada (telefono: Long){
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel: $telefono")
        startActivity(intent)
    }
}