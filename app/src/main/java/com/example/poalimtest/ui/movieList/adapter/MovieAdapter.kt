package com.example.poalimtest.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.poalimtest.R
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.loadImage
import kotlinx.android.synthetic.main.list_movie_item.view.*

class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var data: MutableList<MovieUiItemModel> = mutableListOf()

    var onClickListener: ((MovieUiItemModel) -> Unit)? = null
    var onLikeClickListener: ((MovieUiItemModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_movie_item, parent, false)
        )
    }

    fun updateData(newData: List<MovieUiItemModel>) {
        if (data.containsAll(newData)) return
        data.addAll(newData)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position], onClickListener, onLikeClickListener)
    }


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            movieUiItemModel: MovieUiItemModel,
            onClickListener: ((MovieUiItemModel) -> Unit)?,
            onFavoriteClickListener: ((MovieUiItemModel) -> Unit)?
        ) {

            movieUiItemModel.movieImage.loadImage(itemView.movie_img)

            itemView.like_img.isSelected = movieUiItemModel.isFavorite

            itemView.like_img.setOnClickListener {
                onFavoriteClickListener?.invoke(movieUiItemModel)
            }
            itemView.movie_img.setOnClickListener {
                onClickListener?.invoke(movieUiItemModel)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}