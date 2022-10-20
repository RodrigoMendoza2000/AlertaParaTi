package mx.itesm.gfg.alertaparati.model

import com.tomtom.sdk.common.location.GeoCoordinate

data class Coordenada(
    val longitud: Double,
    val latitud: Double
) {
    fun toGeoCoordinate(): GeoCoordinate {
        return GeoCoordinate(latitud, longitud)
    }
}
