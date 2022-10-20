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
import mx.itesm.gfg.alertaparati.ViewModel.ListaIncidentesVM
import mx.itesm.gfg.alertaparati.databinding.FragmentTraficoBinding
import mx.itesm.gfg.alertaparati.model.Incidente
import mx.itesm.gfg.alertaparati.view.AdaptadorIncidente
import mx.itesm.gfg.alertaparati.view.ListenerRecyclerIncidente


class FragmentTrafico : Fragment(), ListenerRecyclerIncidente {

    // Binding
    private lateinit var binding : FragmentTraficoBinding

    // Viewmodel
    private val viewmodel : ListaIncidentesVM by viewModels()

    // Adaptador
    private lateinit var adaptador : AdaptadorIncidente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTraficoBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRV()
        configurarObservables()
    }


    private fun configurarObservables() {
        // pedirle a viewmodel que regrese la referencia de lista incidentes
        viewmodel.listaIncidentes.observe(requireActivity()){ lista ->
            // convertimos la lista en un arreglo de paises
            val arrIncidentes = lista.toTypedArray()
            // estamos accediendo al adaptador y
            // cambiamos arrIncidentes por el de arriba
            adaptador.arrIncidentes = arrIncidentes
            adaptador.notifyDataSetChanged() // Recargar

            // No hay incidentes
            if (viewmodel.listaIncidentes.value?.isEmpty() == true) {
                binding.tvSinIncidentes.text = "Hasta el momento no ha habido ningún incidente."
            }
        }
    }

    // Se ejecuta cuando ya se crean todos los componentes gráficos
    // Garantiza que ya exiten
    override fun onStart() {
        super.onStart()
        viewmodel.descargarDatosIncidentes()
    }

    private fun configurarRV() {
        val arrIncidentes: Array<Incidente> = emptyArray()
        val layout = LinearLayoutManager(requireActivity())
        // Ya no se declara, se usa la variable de instancia
        binding.rvIncidentes.layoutManager = layout
        adaptador = AdaptadorIncidente(requireActivity(), arrIncidentes)
        binding.rvIncidentes.adapter = adaptador

        adaptador.listener = this
    }

    override fun itemClicked(incidente: Incidente) {
        // Actualizar color del camino
        incidente.setColor()
        // Cambiar fragmento a mapa
        val accion = FragmentTraficoDirections.actionTraficoToFragmentMapaIncidente(incidente.id, incidente.color)
        findNavController().navigate(accion)
    }


}