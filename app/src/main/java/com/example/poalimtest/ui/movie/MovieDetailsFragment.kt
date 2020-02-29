package com.example.poalimtest.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.poalimtest.R
import com.example.poalimtest.ui.movieList.model.ImageUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.loadImage
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(args.movieItem)
    }

    private fun initViews(movieItem: MovieUiItemModel) {
        setImageView(movieItem.movieImage)
        setTitleView(movieItem.title)
        setReleaseDateView(movieItem.date)
        setRatingView(movieItem.rating)
        setDescriptionView(movieItem.description)
    }

    private fun setDescriptionView(description: String) {
        movie_info_description.text = description
    }

    private fun setRatingView(rating: Double) {
        movie_info_rating.text = getString(R.string.rating, rating.toString())

    }

    private fun setReleaseDateView(date: String) {
        movie_info_date.text = getString(R.string.release_date, date)
    }

    private fun setTitleView(title: String) {
        movie_info_title.text = title
    }

    private fun setImageView(imageUiModel: ImageUiModel) {
        imageUiModel.loadImage(movie_info_img)

    }
}