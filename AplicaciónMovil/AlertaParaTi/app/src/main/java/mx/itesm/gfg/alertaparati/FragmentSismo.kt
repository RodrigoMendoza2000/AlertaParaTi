package mx.itesm.gfg.alertaparati

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import mx.itesm.gfg.alertaparati.ViewModel.ListaSismoVM
import mx.itesm.gfg.alertaparati.databinding.FragmentSismoBinding
import mx.itesm.gfg.alertaparati.model.Sismo
import mx.itesm.gfg.alertaparati.view.AdaptadorSismo
import mx.itesm.gfg.alertaparati.Model.ListenerRecyclerSismo

class FragmentSismo : Fragment(), ListenerRecyclerSismo {

    // binding
    private lateinit var binding : FragmentSismoBinding

    //viewmodel
    private val viewmodel : ListaSismoVM by viewModels()

    // adaptador
    private lateinit var adaptador : AdaptadorSismo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSismoBinding.inflate(layoutInflater)
        //se prende
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(binding.root)
        configurarRV()
        configurarObservables()
    }


    private fun configurarObservables() {
        // pedirle a viewmodel que regrese la referencia de lista paises
        viewmodel.listaSismos.observe(requireActivity()){ lista ->
            // convertimos la lista en un arreglo de paises
            val arrSismo = lista.toTypedArray()
            // estamos accediendo al adaptador y
            // cambiamos arrPaises por el de arriba
            adaptador.arrSismos = arrSismo
            println(arrSismo)
            println(arrSismo[0])
            println(arrSismo.size)
            println(lista.size)
            adaptador.notifyDataSetChanged() // Recarga todo
            //aqui se apaga
        }
    }

    // Se ejecuta cuando ya se crean todos los componentes gráficos
    // Garantiza que ya exiten
    override fun onStart() {
        super.onStart()
        viewmodel.descargarDatoSismos()

    }

    private fun configurarRV() {
        val arrSismos: Array<Sismo> = emptyArray()
        val layout = LinearLayoutManager(requireActivity())
        binding.rvSismos.layoutManager = layout
        adaptador = AdaptadorSismo(requireActivity(), arrSismos)
        binding.rvSismos.adapter = adaptador
        val separador = DividerItemDecoration(requireActivity(), layout.orientation)

        adaptador.listener = this
    }

    override fun itemClicked(sismo: Sismo) {
        // Cambiar fragmento a mapa
        val accion = FragmentSismoDirections.actionFragmentSismoToFragmentMapaSismo(sismo.latitud, sismo.longitud)
        findNavController().navigate(accion)
    }
}