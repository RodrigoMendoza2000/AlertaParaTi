package mx.itesm.gfg.alertaparati.view

import mx.itesm.gfg.alertaparati.model.Incidente
import mx.itesm.gfg.alertaparati.model.Sismo

interface ListenerRecyclerIncidente
{
    fun itemClicked(incidente: Incidente)

}