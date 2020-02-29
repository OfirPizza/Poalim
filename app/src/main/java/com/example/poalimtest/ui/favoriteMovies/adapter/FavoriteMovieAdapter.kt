package com.example.poalimtest.ui.favoriteMovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.poalimtest.R
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.loadImage
import kotlinx.android.synthetic.main.list_favorite_movie_item.view.*

class FavoriteMovieAdapter :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private var data: List<MovieUiItemModel> = listOf()
    var onClickListener: ((MovieUiItemModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        return FavoriteMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_favorite_movie_item, parent, false)
        )
    }

    fun updateData(newData: List<MovieUiItemModel>) {
        data = newData
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(data[position], onClickListener)
    }

    class FavoriteMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            favoriteMovieModel: MovieUiItemModel,
            onClickListener: ((MovieUiItemModel) -> Unit)?
        ) {

            favoriteMovieModel.movieImage.loadImage(itemView.favorite_movie_img)

            itemView.favorite_movie_img.setOnClickListener {
                onClickListener?.invoke(favoriteMovieModel)
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}