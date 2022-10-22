package mx.itesm.gfg.alertaparati.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.itesm.gfg.alertaparati.Model.ListenerRecyclerIncidente
import mx.itesm.gfg.alertaparati.R
import mx.itesm.gfg.alertaparati.model.Incidente

class AdaptadorIncidente (val context: Context,
                          var arrIncidentes: Array<Incidente>):
    RecyclerView.Adapter<AdaptadorIncidente.RenglonIncidente>() {
    // Listener para los clicks
    var listener : ListenerRecyclerIncidente? = null



    // Una vista para un renglón
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonIncidente {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_trafico, parent, false)
        return RenglonIncidente(vista)
    }

    // Poblar la vista (renglón) con los datos del índice 'position'
    override fun onBindViewHolder(holder: RenglonIncidente, position: Int) {
        val incidente = arrIncidentes[position]
        holder.set(incidente)
        holder.renglonIncidente.setOnClickListener {
            listener?.itemClicked(incidente)
        }
    }

    // El nùmero de renglones que tendrá el recyclerview
    override fun getItemCount(): Int {
        return arrIncidentes.size
    }

    class RenglonIncidente(var renglonIncidente: View) : RecyclerView.ViewHolder(renglonIncidente)
    {
        fun set(incidente: Incidente) {
            val tvAccidente = renglonIncidente.findViewById<TextView>(R.id.tvAccidente)
            val tvLugar = renglonIncidente.findViewById<TextView>(R.id.tvLugar)
            val tvTiempo = renglonIncidente.findViewById<TextView>(R.id.tvTiempo)
            val imgAccidente = renglonIncidente.findViewById<ImageView>(R.id.imgTrafico)
            tvAccidente.text = incidente.tipo_incidente.replaceFirstChar { it.uppercase() }
            tvLugar.text = incidente.desde_lugar
            tvTiempo.text = incidente.tiempo_inicio
            // Descargar el icono desde el url y ponerla en imgAccidente
            renglonIncidente.findViewById<ImageView>(R.id.imgTrafico).setImageResource(R.drawable.accident2_sign)
            val url = "https://galaflores.github.io/Iconos_Trafico/" + incidente.tipo_id.toString() + ".png"
            Glide.with(renglonIncidente.context).load(url).into(imgAccidente)

        }
    }
}