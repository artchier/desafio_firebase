package br.com.digitalhouse.desafiofirebase.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentEditBinding
import br.com.digitalhouse.desafiofirebase.model.Game
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog

class EditFragment : Fragment() {
    private val myViewModel: MyViewModel by navGraphViewModels(R.id.navigation2)
    private var _binding: FragmentEditBinding? = null
    private lateinit var storageReference: StorageReference
    private lateinit var url: String
    private lateinit var lastTitle: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root

        try {
            url = arguments?.getString("cover")!!
            Glide.with(this).load(url)
                .into(binding.ivCoverEdit)
            binding.name.text =
                Editable.Factory.getInstance().newEditable(arguments?.getString("title"))
            binding.createdAt.text = Editable.Factory.getInstance()
                .newEditable(arguments?.getString("year")!!.substringAfter("Lançamento: "))
            binding.description.text =
                Editable.Factory.getInstance().newEditable(arguments?.getString("description"))

            lastTitle = binding.name.text.toString()
        } catch (ignored: Exception) {
            lastTitle = ""
            url = ""
        }
        myViewModel.getStorageReferenceTask()

        view.setBackgroundResource(R.drawable.splash_firebase)
        binding.ivCoverEdit.setOnClickListener {
            if (binding.name.text.toString() != "") {
                val intent = Intent()
                intent.type = "image/"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, ""), 1)
            } else {
                Toast.makeText(activity, "Preencha os campos vazios", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btSaveGame.setOnClickListener {
            val newGame = Game(
                url,
                binding.name.text.toString(),
                binding.createdAt.text.toString(),
                binding.description.text.toString()
            )

            if (lastTitle == binding.name.text.toString()) {
                myViewModel.createGameTask(newGame, binding.name.text.toString())
            } else {
                myViewModel.removeGameTask(lastTitle)
                myViewModel.createGameTask(newGame, binding.name.text.toString())
            }
            myViewModel.getLastGame(newGame)
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

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        storageReference =
            FirebaseStorage.getInstance().getReference(binding.name.text.toString())
        if (requestCode == 1 && data != null) {
            val spotsDialog = SpotsDialog.Builder()
                .setMessage("Aguarde")
                .setContext(activity).build()
            spotsDialog.show()
            val uploadTask =
                storageReference.putFile(data.data!!)
            uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Chegando", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Deu ruim", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    spotsDialog.dismiss()
                    Glide.with(this).load(task.result).into(binding.ivCoverEdit)
                    url = task.result.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}