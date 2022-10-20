package mx.itesm.gfg.alertaparati.model

data class Sismo(
     val id: String,
     val ubicacion: String,
     val magnitud: Float,
     val longitud: Float,
     val latitud: Float,
     val fecha: String,
)
