package mx.itesm.gfg.alertaparati.model

import com.google.gson.annotations.SerializedName

data class Noticia(
    @SerializedName("noticia")
    val nombreNoticia: String,
    @SerializedName("descripcionNoticia")
    val descripcionNoticia: Int,
    /** Porque tenemos un diccionario con la info de la noticia
     * Si no pasan un diccionario lo crea vacío */
    @SerializedName("accidenteInfo")
    val infoNoticia: Map<String, String> = mapOf(),
    val idNoticia: Int = 0,
    val urlNoticia: String = ""
)
