package com.example.poalimtest.ui.favoriteMovies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.poalimtest.R
import com.example.poalimtest.ui.favoriteMovies.adapter.FavoriteMovieAdapter
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMoviesFragment : Fragment(R.layout.fragment_favorite_movies) {

    private val viewModel: FavoriteMoviesViewModel by viewModel()

    private val favoriteMovieAdapter = FavoriteMovieAdapter().apply {
        onClickListener = { this@FavoriteMoviesFragment.onFavoriteMovieClick(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initViews()
    }

    private fun initViews() {
        favorite_movie_recycler.adapter = favoriteMovieAdapter
    }

    private fun initViewModels() {
        viewModel.favoriteMovieListLiveData.observe(viewLifecycleOwner, Observer { onReceivedFavoriteMovies(it) })
    }

    private fun onReceivedFavoriteMovies(favoriteMovies: List<MovieUiItemModel>) {
        favoriteMovieAdapter.updateData(favoriteMovies)
        favoriteMovieAdapter.notifyDataSetChanged()
    }

    private fun onFavoriteMovieClick(favoriteMovieItem: MovieUiItemModel) {
        findNavController().navigate(FavoriteMoviesFragmentDirections.actionToMovieInfoFragment(favoriteMovieItem))
    }
}