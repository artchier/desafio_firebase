package br.com.digitalhouse.desafiofirebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentEditBinding
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel

class EditFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private var _binding: FragmentEditBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btSaveGame.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.description.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        /*myViewModel._parametersComic.observe(viewLifecycleOwner) {
            Glide.with(this).load(it[5]).into(view.ivThumbnailBigger)
        }*/

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}