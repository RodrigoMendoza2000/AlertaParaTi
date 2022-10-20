package mx.itesm.gfg.alertaparati

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import mx.itesm.gfg.alertaparati.ViewModel.ListaClimaVM
import mx.itesm.gfg.alertaparati.databinding.FragmentClimaBinding
import mx.itesm.gfg.alertaparati.model.Clima
import mx.itesm.gfg.alertaparati.view.AdaptadorClima
import kotlin.math.roundToInt


class fragment_clima : Fragment() {

    // binding
    private lateinit var binding : FragmentClimaBinding

    //viewmodel
    private val viewmodel : ListaClimaVM by viewModels()

    // adaptador
    private lateinit var adaptador : AdaptadorClima


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClimaBinding.inflate(layoutInflater)
        //se prende
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(binding.root)
        configurarRV()
        configurarObservables()
    }

    private fun ponerDatosActuales() {
        println(viewmodel.listaClimaActual.value?.get(0)?.tem_actual.toString().plus("°C"))
        binding.tvGradoActual.setText(viewmodel.listaClimaActual.value?.get(0)?.tem_actual.toString().plus("°C"))
        binding.tvTemMax.setText(viewmodel.listaClimaActual.value?.get(0)?.tem_maxima?.roundToInt().toString().plus("°C"))
        binding.tvTemMin.setText(viewmodel.listaClimaActual.value?.get(0)?.tem_minima?.roundToInt().toString().plus("°C"))
        binding.tvIndiceUV.setText(viewmodel.listaClimaActual.value?.get(0)?.indice_actual.toString())
        binding.tvIndiceCo2.setText(viewmodel.listaClimaActual.value?.get(0)?.calidad_actual.toString().plus("μg/m3"))
        binding.tvIndiceLluvia.setText(viewmodel.listaClimaActual.value?.get(0)?.lluvia_actual.toString().plus("mm"))

    }

    private fun actualizarImagen() {
        var url = ""
        if (viewmodel.listaClimaActual.value?.get(0)?.lluvia_actual!! > 0.toFloat()) {
            url = "https://galaflores.github.io/Iconos_Trafico/lluvia_dos.png"
        } else if (viewmodel.listaClimaActual.value?.get(0)?.hora_actual!!.split(":")[0].toInt() < 19) {
            if (viewmodel.listaClimaActual.value?.get(0)?.tem_actual!! < 19) {
                url = "https://galaflores.github.io/Iconos_Trafico/nublado_dos.png"
            } else if (viewmodel.listaClimaActual.value?.get(0)?.tem_actual!! >= 19) {
                url = "https://galaflores.github.io/Iconos_Trafico/soleado.png"
            }
        } else {
            url = "https://galaflores.github.io/Iconos_Trafico/nublado_noche.png"
        }
        Glide.with(requireContext()).load(url).into(binding.imgEstadoClima)
    }


    private fun configurarObservables() {
        // pedirle a viewmodel que regrese la referencia de lista paises
        viewmodel.listaClimas.observe(requireActivity()){ lista ->
            // convertimos la lista en un arreglo de paises
            val arrClima = lista.toTypedArray()
            // estamos accediendo al adaptador y
            // cambiamos arrPaises por el de arriba
            adaptador.arrClimas = arrClima
            adaptador.notifyDataSetChanged() // Recarga todo
            //aqui se apaga
        }
        viewmodel.listaClimaActual.observe(requireActivity()){lista->
            ponerDatosActuales()
            actualizarImagen()
        }

    }

    // Se ejecuta cuando ya se crean todos los componentes gráficos
    // Garantiza que ya exiten
    override fun onStart() {
        super.onStart()
        viewmodel.descargarDatoClima()
        viewmodel.descargarDatosClimaActual()

    }



    private fun configurarRV() {
        val arrClimas: Array<Clima> = emptyArray()
        val layout = LinearLayoutManager(requireActivity())
        // Ya no se declara, se usa la variable de instancia
        layout.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvClima.layoutManager = layout
        adaptador = AdaptadorClima(requireActivity(), arrClimas)
        binding.rvClima.adapter = adaptador
    }

}