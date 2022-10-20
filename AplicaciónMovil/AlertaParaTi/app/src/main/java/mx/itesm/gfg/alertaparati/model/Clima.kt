package mx.itesm.gfg.alertaparati.model

data class Clima(
    val id: String,
    val temperatura: Int,
    val porcentaje_lluvia: Float,
    val indice_UV: Float,
    val calidad_aire: Float,
    val fecha: String,
    val hora: String,

)
