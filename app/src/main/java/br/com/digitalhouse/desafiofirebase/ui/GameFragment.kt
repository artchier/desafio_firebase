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
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentGameBinding
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel

class GameFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private lateinit var adapter: GameAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var offset = 0
    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
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

        /*adapter = GameAdapter(this, myViewModel, view, listGames)*/

        gridLayoutManager = GridLayoutManager(view.context, 2)
        binding.rvGame.adapter = adapter
        binding.rvGame.layoutManager = gridLayoutManager

        setScroller()

        return view
    }

    private fun setScroller() {
        binding.rvGame.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val lItem = gridLayoutManager.itemCount
                    val vItem =
                        gridLayoutManager.findFirstVisibleItemPosition()
                    val itens = adapter.itemCount
                    if (lItem + vItem >= itens) {
                        offset += 1
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}