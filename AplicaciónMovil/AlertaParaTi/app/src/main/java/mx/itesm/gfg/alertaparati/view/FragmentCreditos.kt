package mx.itesm.gfg.alertaparati.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.gfg.alertaparati.databinding.FragmentCreditosBinding
import java.util.zip.Inflater


class FragmentCreditos : Fragment() {
    private lateinit var binding: FragmentCreditosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditosBinding.inflate(inflater, container, false)

        return binding.root

    }


}