package mx.itesm.gfg.alertaparati.model

data class Incidente(
    val id: String,
    val tipo_id: Int,
    val tipo_incidente: String,
    val tiempo_inicio: String,
    val tiempo_final: String,
    val desde_lugar: String,
    val hasta_lugar: String,
    var color: Int
) {
    fun setColor() {
        color = when (tipo_id) {
            1 -> 0xE84927
            2 -> 0xA5A5A5
            3 -> 0xF79326
            4 -> 0x688EBE
            6 -> 0xA7C39B
            8 -> 0xF44336
            9 -> 0xF4BF6D
            11 -> 0x63B5D6
            14 -> 0xB2B252
            else -> 0xFFFFFF
        }
    }

}


