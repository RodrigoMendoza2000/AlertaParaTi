package mx.itesm.gfg.alertaparati.view

import mx.itesm.gfg.alertaparati.model.Sismo

interface ListenerRecyclerSismo {
    fun itemClicked(sismo: Sismo)
}