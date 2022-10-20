package mx.itesm.gfg.alertaparati

import android.app.UiModeManager.MODE_NIGHT_NO
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import mx.itesm.gfg.alertaparati.databinding.ActivityMainBinding
import mx.itesm.gfg.alertaparati.model.DatosSesion
import mx.itesm.gfg.alertaparati.model.MandaDatosBD
import mx.itesm.gfg.alertaparati.model.MandarDatosAppAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    // Datos de la sesion
    var sesion = DatosSesion()

    // Clase para mandar datos a la base de datos
    private var mandaDatosFrag = MandaDatosBD()

    // Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Quitar modo noche
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_sos, R.id.navigation_bot
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        // Para suscribirse a cierto tema
        Firebase.messaging.subscribeToTopic("alertaParaTi")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                println(msg)
            }

        // Actualizar fecha y hora de inicio de sesion
        sesion.actualizarDatosInicio()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()
        // Actualizar fecha y hora de fin de sesion
        sesion.actualizarDatosFin()

        // Mandar datos de sesion
        mandaDatosFrag.mandarDatosSesion(sesion)
        println(sesion)
    }


}