package br.com.digitalhouse.desafiofirebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentGameBinding
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel

class GameFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private lateinit var adapter: GameAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myViewModel.connectDBTask()
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = GameAdapter(this, myViewModel, binding)

        gridLayoutManager = GridLayoutManager(view.context, 2)
        binding.rvGame.adapter = adapter
        binding.rvGame.layoutManager = gridLayoutManager

        myViewModel.getGamesTask(binding)

        myViewModel.allGames.observe(viewLifecycleOwner) {
            adapter.addGames(it)
        }

        binding.fabAddGame.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_editFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        myViewModel._scrollCoordinates.observe(viewLifecycleOwner) {
            binding.rvGame.scrollTo(it[0], it[1])
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}