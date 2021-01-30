package br.com.digitalhouse.desafiofirebase.ui

import android.os.Bundle
import android.text.method.MovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentDetailBinding
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private var _binding: FragmentDetailBinding? = null
    private lateinit var cover: String
    private lateinit var title: String
    private lateinit var year: String
    private lateinit var description: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tbDetail.setupWithNavController(
            findNavController(),
            AppBarConfiguration(findNavController().graph)
        )
        binding.tbDetail.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.back, null)

        cover = arguments?.getString("cover")!!

        Glide.with(this).load(cover).into(binding.ivDetailCover)
        binding.tvGameTitle.text = arguments?.getString("title")
        binding.tvDetailTitle.text = arguments?.getString("title")
        binding.tvDetailYear.text = "Lançamento: ${arguments?.getString("year")}"
        binding.tvDetailDescription.text = arguments?.getString("description")

        title = binding.tvGameTitle.text.toString()
        year = binding.tvDetailYear.text.toString()
        description = binding.tvDetailDescription.text.toString()

        myViewModel.lastGame.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.cover).into(binding.ivDetailCover)
            binding.tvGameTitle.text = it.title
            binding.tvDetailTitle.text = it.title
            binding.tvDetailYear.text = "Lançamento: ${it.year}"
            binding.tvDetailDescription.text = it.description
        }

        binding.fabEditGame.setOnClickListener {
            val bundle = bundleOf(
                "cover" to cover,
                "title" to title,
                "year" to year,
                "description" to description
            )
            findNavController().navigate(R.id.action_detailFragment_to_editFragment, bundle)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}