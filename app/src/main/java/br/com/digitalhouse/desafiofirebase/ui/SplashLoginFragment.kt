package br.com.digitalhouse.desafiofirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentRegisterBinding
import br.com.digitalhouse.desafiofirebase.databinding.FragmentSplashloginBinding

class SplashLoginFragment : Fragment() {
    private var _binding: FragmentSplashloginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashloginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_splashloginFragment_to_gameFragment)
        }
        binding.btCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_splashloginFragment_to_registerFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}