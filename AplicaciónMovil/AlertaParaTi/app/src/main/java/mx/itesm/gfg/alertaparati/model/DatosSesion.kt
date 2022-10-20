package mx.itesm.gfg.alertaparati.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DatosSesion (
    var fecha_inicio: String = "",
    var hora_inicio: String = "",
    var fecha_fin: String = "",
    var hora_fin: String = ""
) {
    fun actualizarDatosInicio() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHora = LocalDateTime.now().format(formatter).split(" ")
        fecha_inicio = fechaHora[0]
        hora_inicio = fechaHora[1]
        fecha_fin = ""
        hora_fin = ""
    }

    fun actualizarDatosFin() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHora = LocalDateTime.now().format(formatter).split(" ")
        fecha_fin = fechaHora[0]
        hora_fin = fechaHora[1]
    }
}