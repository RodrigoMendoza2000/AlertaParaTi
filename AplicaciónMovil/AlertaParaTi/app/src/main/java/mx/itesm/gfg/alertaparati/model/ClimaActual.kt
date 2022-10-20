package mx.itesm.gfg.alertaparati.model

import com.google.gson.annotations.SerializedName

data class ClimaActual(
    @SerializedName ("temperatura")
    val tem_actual: Int,
    @SerializedName ("porcentaje_lluvia")
    val lluvia_actual: Float,
    @SerializedName ("indice_UV")
    val indice_actual: Float,
    @SerializedName ("calidad_aire")
    val calidad_actual: Float,
    @SerializedName ("fecha")
    val fecha_actual: String,
    @SerializedName ("hora")
    val hora_actual: String,
    @SerializedName ("temperatura_maxima")
    val tem_maxima: Float,
    @SerializedName ("temperatura_minima")
    val tem_minima: Float

)
