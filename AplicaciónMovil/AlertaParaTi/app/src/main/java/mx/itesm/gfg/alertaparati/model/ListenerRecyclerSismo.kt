package mx.itesm.gfg.alertaparati.Model

import mx.itesm.gfg.alertaparati.model.Sismo

interface ListenerRecyclerSismo {
    fun itemClicked(sismo: Sismo)
}