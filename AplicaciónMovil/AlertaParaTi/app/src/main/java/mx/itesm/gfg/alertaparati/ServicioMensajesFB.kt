package mx.itesm.gfg.alertaparati

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ServicioMensajesFB: FirebaseMessagingService() {
    private val channelName="alertasPC"
    private val channelId = "mx.itesm.gfg.alertaparati"
    override fun onNewToken(token: String) {
        println("Token de este dispositivo: $token")

//        enviarTokenAPI(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        println("Llega NOTIFICACIÓN remota")
        if (message.notification != null){
            generarNotificacion(message)
        }
    }

    private fun generarNotificacion(message: RemoteMessage) {
        //Abre la app
        //Main activity es la ventana que va a cargar
        val intent = Intent(this, MainActivity::class.java)
        //Borra toda la actividad
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        //Intermediario
        val pendingIntent = PendingIntent.getActivity(this,
            0, intent, PendingIntent.FLAG_MUTABLE)

        var builder = NotificationCompat.Builder(this, channelId)
            //que icono se mostrará
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            //Desaparece de las notificaciones
            .setAutoCancel(true)
            //Permite poner patron de vibracion del telefono
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            //Si se mandan muchas, solo queda activa una
            .setOnlyAlertOnce(true)
            //Le digo donde esta la info para responder al click
            .setContentIntent(pendingIntent)
        builder = builder.setContent(crearVistaRemota(message))

        val admNotificaciones = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalNotificaciones = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            //ad mi nivel sistema operativo
            admNotificaciones.createNotificationChannel(canalNotificaciones)
        }
        //GEnera la notificacion localmente
        admNotificaciones.notify(0, builder.build())
    }
    //Genera la GUI para mostrar la notificacion
    @SuppressLint("RemoteViewLayout")
    private fun crearVistaRemota(message: RemoteMessage): RemoteViews {
        val titulo = message.notification?.title!!
        val mensaje = message.notification?.body!!
        val vistaRemota = RemoteViews("mx.itesm.gfg.alertaparati", R.layout.notificacion)
        vistaRemota.setTextViewText(R.id.txttitulonoti, titulo)
        vistaRemota.setTextViewText(R.id.txtmsjnoti, mensaje)
        vistaRemota.setImageViewResource(R.id.campnoti, R.drawable.logo)
        return vistaRemota
    }


}