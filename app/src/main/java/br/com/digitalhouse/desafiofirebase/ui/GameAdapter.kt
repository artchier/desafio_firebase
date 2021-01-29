package br.com.digitalhouse.desafiofirebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.desafiofirebase.R
import br.com.digitalhouse.desafiofirebase.databinding.FragmentGameBinding
import br.com.digitalhouse.desafiofirebase.model.Game
import br.com.digitalhouse.desafiofirebase.viewmodel.MyViewModel
import java.util.*

class GameAdapter(
    private val context: GameFragment,
    private val myViewModel: MyViewModel,
    private val inflate: FragmentGameBinding
) :

    RecyclerView.Adapter<GameAdapter.ComicViewHolder>() {
    private var listGames = ArrayList<Game>()

    class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCoverItem: ImageView = view.findViewById(R.id.ivCoverItem)
        val tvTitleItem: TextView = view.findViewById(R.id.tvTitleItem)
        val tvYearItem: TextView = view.findViewById(R.id.tvYearItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)

        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int = listGames.size

    fun addGames(games: ArrayList<Game>) {
        listGames.clear()
        listGames.addAll(games)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val games = listGames[position]

        inflate.pbGame.visibility = GONE
        holder.ivCoverItem.setImageResource(R.drawable.splash_firebase)
        holder.tvTitleItem.text = games.title
        holder.tvYearItem.text = games.year

        holder.itemView.setOnClickListener {
            val bundle = bundleOf(
                "cover" to games.cover,
                "title" to games.title,
                "year" to games.year,
                "description" to games.description
            )
            context.findNavController().navigate(R.id.action_gameFragment_to_detailFragment, bundle)
        }

        /*Glide.with(context)
            .load("${comics.thumbnail.path}.${comics.thumbnail.extension}")
            .into(holder.ivCover)*/

        myViewModel.updateScrollCoordinates(
            intArrayOf(
                inflate.rvGame.scrollX,
                inflate.rvGame.scrollY
            )
        )
    }
}