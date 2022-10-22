package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import mx.itesm.gfg.alertaparati.R
import mx.itesm.gfg.alertaparati.ViewModel.MandaDatosBD


class FragmentNoticias : Fragment() {

    // Clase para mandar datos a la base de datos
    private var mandaDatosBD = MandaDatosBD()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_notificaciones, container, false)
        val mWebView = v.findViewById<View>(R.id.webView) as WebView
        mWebView.loadUrl("https://atizapan.gob.mx")
        // Enable JavaScript
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true

        // Esto forza a que los links se abran en WebView y no en el browser
        mWebView.webViewClient = WebViewClient()
        mWebView.canGoBack()
        mWebView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && mWebView.canGoBack()) {
                mWebView.goBack()
                return@OnKeyListener true
            }
            false
        })
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Cambia el título mostrado en la toolbar
        requireActivity().title = "Sitio web de Atizapán de Zaragoza"
    }

    override fun onStart() {
        super.onStart()
        mandaDatosBD.mandarDatosFragmento("Noticias")
    }
}
