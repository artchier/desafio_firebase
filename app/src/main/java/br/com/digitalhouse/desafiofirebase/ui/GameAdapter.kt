package br.com.digitalhouse.desafiofirebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentGameBinding
import br.com.digitalhouse.desafiofirebase.model.Game
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class GameAdapter(
    private val context: GameFragment,
    private val myViewModel: MyViewModel,
    private val inflate: FragmentGameBinding,
    private var listGames: ArrayList<Game>
) :

    RecyclerView.Adapter<GameAdapter.ComicViewHolder>() {
    class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivGameCover: ImageView = view.findViewById(R.id.ivGameCover)
        val ivGameTitle: TextView = view.findViewById(R.id.tvGameTitle)
        val tvDetail: TextView = view.findViewById(R.id.tvDetail)
        val tvYear: TextView = view.findViewById(R.id.tvYear)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var coverGame: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)

        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int = listGames.size

    /*fun addComics(results: ArrayList<Results>) {
        listGames.addAll(results)
        notifyDataSetChanged()
    }*/

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comics = listGames[position]
        var number: String? = null
        /*try {
            val hashCoordinates: IntArray = findHash(comics.title)
            number = StringBuilder(comics.title).substring(hashCoordinates[0], hashCoordinates[1])
        } catch (ignored: Exception) {
        }

        Glide.with(context)
            .load("${comics.thumbnail.path}.${comics.thumbnail.extension}")
            .into(holder.ivCover)

        holder.tvNumber.text = number

        holder.itemView.setOnClickListener {
            title = when (comics.title) {
                null -> "No title available"
                else -> comics.title
            }

            description = when (comics.description) {
                null -> "No description available"
                else -> comics.description
            }

            coverGame = try {
                "${comics.images[0].path}.${comics.images[0].extension}"
            } catch (ignored: Exception) {
                ""
            }

            myViewModel.addParameters(
                arrayListOf(
                    title,
                    description.replace("<br>", ""),
                    SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date),
                    NumberFormat.getCurrencyInstance(Locale.US).format(price),
                    pageCount.toString(),
                    thumbnail,
                    coverGame
                )
            )*/

            myViewModel.updateScrollCoordinates(
                intArrayOf(
                    inflate.rvGame.scrollX,
                    inflate.rvGame.scrollY
                )
            )
            findNavController(context).navigate(R.id.action_gameFragment_to_detailFragment)
        //}
    }

    private fun findHash(string: String): IntArray {
        var hashStart = 0
        var hashEnd = 0

        for (index in 0..string.length) {
            if (string[index] == '#') {
                hashStart = index
                break
            }
        }

        for (index in hashStart until string.length) {
            if (string[index] == ' ') {
                hashEnd = index
                break
            }
            if (index + 1 == string.length) {
                hashEnd = string.length
                break
            }
        }

        return intArrayOf(hashStart, hashEnd)
    }
}