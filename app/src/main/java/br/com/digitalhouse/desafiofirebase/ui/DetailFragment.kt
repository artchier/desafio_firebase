package br.com.digitalhouse.desafiofirebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentDetailBinding
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel

class DetailFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private var _binding: FragmentDetailBinding? = null

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
        binding.tbDetail.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.back, null)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        myViewModel.allGames.observe(viewLifecycleOwner){
            binding.ivDetailCover.setImageResource(R.drawable.splash_firebase)
            binding.tvGameTitle.text = it[0].title
            binding.tvDetailYear.text = it[0].year
            binding.tvDescription.text = it[0].description
        }

        /*myViewModel._parametersComic.observe(viewLifecycleOwner) {
            view.tvDetail.text = it[0]
            view.tvDescription.text = it[1]
            view.tvPublishedValue.text = if (it[2] == "0") "No date available" else it[2]
            view.tvPriceValue.text = if (it[3] == "0") "" else it[3]
            view.tvPagesValue.text = it[4]
            Glide.with(this).load(it[5]).into(view.ivThumbnail)
            if (it[6] != "") Glide.with(this).load(it[6]).into(view.ivPromoArt)
        }*/

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}