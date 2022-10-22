package mx.itesm.gfg.alertaparati.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.gfg.alertaparati.Model.ListenerRecyclerSismo
import mx.itesm.gfg.alertaparati.R
import mx.itesm.gfg.alertaparati.model.Sismo

class AdaptadorSismo (val context: Context,
                      var arrSismos: Array<Sismo>):
    RecyclerView.Adapter<AdaptadorSismo.RenglonSismo>() {
    // Listener para los clicks
    var listener : ListenerRecyclerSismo? = null

    // Una vista para un renglón
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonSismo {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_sismo, parent, false)
        return RenglonSismo(vista)
    }

    // Poblar la vista (renglón) con los datos del índice 'position'
    override fun onBindViewHolder(holder: RenglonSismo, position: Int) {
        val sismo = arrSismos[position]
        holder.set(sismo)
        holder.renglonSismo.setOnClickListener{
            listener?.itemClicked(sismo)
        }
    }

    // El nùmero de renglones que tendrá el recyclerview
    override fun getItemCount(): Int {
        return arrSismos.size
    }

    class RenglonSismo(var renglonSismo: View) : RecyclerView.ViewHolder(renglonSismo)
    {
        fun set(sismo: Sismo) {
            val tvUbicacion = renglonSismo.findViewById<TextView>(R.id.tvUbicacion)
            val tvMagnitud = renglonSismo.findViewById<TextView>(R.id.tvMagnitd)
            val tvFecha = renglonSismo.findViewById<TextView>(R.id.tvFecha)
            val imgSismo = renglonSismo.findViewById<ImageView>(R.id.imgSismo)

            // Recortar string de ubicacion
            var ubicacionDividida: List<String> = sismo.ubicacion.split(" ")
            val index = ubicacionDividida.indexOf("of")
            if (index != -1) {
                ubicacionDividida = ubicacionDividida.slice((index + 1) until ubicacionDividida.size)
            }
            val ubicacion = ubicacionDividida.joinToString(" ")

            // Asignar textos
            tvUbicacion.text = ubicacion
            tvMagnitud.text = sismo.magnitud.toString()
            tvFecha.text = sismo.fecha
            // Descargar el icono desde el url y ponerla en imgAccidente
            renglonSismo.findViewById<ImageView>(R.id.imgSismo).setImageResource(R.drawable.sismos_pais)

        }
    }
}