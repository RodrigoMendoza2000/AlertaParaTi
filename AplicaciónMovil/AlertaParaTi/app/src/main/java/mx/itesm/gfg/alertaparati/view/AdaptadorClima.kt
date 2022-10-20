package mx.itesm.gfg.alertaparati.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.itesm.gfg.alertaparati.R
import mx.itesm.gfg.alertaparati.model.Clima

class AdaptadorClima (val context: Context,
                      var arrClimas: Array<Clima>):
    RecyclerView.Adapter<AdaptadorClima.RenglonClima>() {



    // Una vista para un renglón
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonClima {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_clima, parent, false)
        return RenglonClima(vista)
    }

    // Poblar la vista (renglón) con los datos del índice 'position'
    override fun onBindViewHolder(holder: RenglonClima, position: Int) {
        val clima = arrClimas[position]
        holder.set(clima)
    }

    // El nùmero de renglones que tendrá el recyclerview
    override fun getItemCount(): Int {
        return arrClimas.size
    }

    class RenglonClima(var renglonClima: View) : RecyclerView.ViewHolder(renglonClima)
    {
        fun set(clima: Clima) {
            val tvGrado = renglonClima.findViewById<TextView>(R.id.tvGrado)
            val tvHora = renglonClima.findViewById<TextView>(R.id.tvHora)
            val imgClima = renglonClima.findViewById<ImageView>(R.id.imgClima)
            tvGrado.text = clima.temperatura.toString().plus("°C")
            tvHora.text = clima.hora
            // Descargar el icono desde el url y ponerla en imgAccidente
            var url = ""
            if (clima.porcentaje_lluvia > 0.toFloat()) {
                url = "https://galaflores.github.io/Iconos_Trafico/lluvia_dos.png"
            } else if (clima.hora.split(":")[0].toInt() < 19) {
                if (clima.temperatura < 19) {
                    url = "https://galaflores.github.io/Iconos_Trafico/nublado_dos.png"
                } else if (clima.temperatura >= 19) {
                    url = "https://galaflores.github.io/Iconos_Trafico/soleado.png"
                }
            } else {
                url = "https://galaflores.github.io/Iconos_Trafico/nublado_noche.png"
            }

            Glide.with(renglonClima.context).load(url).into(imgClima)
        }
    }
}