package com.example.poalimtest.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.poalimtest.R
import com.example.poalimtest.connactivity.ConnectivityRepoImpl.ConnectionState
import com.example.poalimtest.ui.movieList.adapter.MovieAdapter
import com.example.poalimtest.ui.movieList.model.MovieListUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewModel: MovieViewModel by viewModel()

    private val movieAdapter = MovieAdapter().apply {
        onClickListener = { this@MovieFragment.onMovieClicked(it) }
        onLikeClickListener = { this@MovieFragment.onLikeClicked(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    private fun initViews() {
        movie_recycler_view.adapter = movieAdapter
        movie_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                movie_recycler_view?.let {
                    if (!it.canScrollVertically(1)) {
                        viewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel.apply {
            movieListLiveData.observe(viewLifecycleOwner, Observer { onReceivedMovies(it) })
            refreshUiLiveData.observe(viewLifecycleOwner, Observer { refreshUi()})
            isNetworkAvailableLiveData.observe(viewLifecycleOwner, Observer { onNetworkStatusChanged(it) })
        }
    }

    private fun onNetworkStatusChanged(state: ConnectionState) {
        if (state == ConnectionState.LOST) return
        viewModel.getMovieList()
    }

    private fun onReceivedMovies(movieList: MovieListUiModel) {
        movieAdapter.updateData(movieList.movieList)
        movieAdapter.notifyDataSetChanged()
    }

    private fun onMovieClicked(movieItem: MovieUiItemModel) {
        findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToMovieInfoFragment(
                movieItem
            )
        )
    }

    private fun onLikeClicked(movieItem: MovieUiItemModel) {
        viewModel.updateFavoriteMovie(movieItem)
    }

    private fun refreshUi() {
        movieAdapter.notifyDataSetChanged()
    }
}