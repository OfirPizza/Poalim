package com.example.poalimtest.ui.favoriteMovies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private val TAG = FavoriteMoviesViewModel::class.java.simpleName

class FavoriteMoviesViewModel(private val favoriteMovieRepo: FavoriteMovieRepo) : ViewModel() {

    private val favoriteMovieListMutableLiveData = MutableLiveData<List<MovieUiItemModel>>()
    val favoriteMovieListLiveData: LiveData<List<MovieUiItemModel>> = favoriteMovieListMutableLiveData

    init {
        getFavoriteMovies()
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun getFavoriteMovies() {
        CoroutineScope(IO).launch {
            val results = favoriteMovieRepo.getFavoriteMovieList()
            when (results) {
                is Result.Value -> postFavoriteMovies(results.value)
                is Result.Error -> Log.e(TAG, "Failed to get favorite movie list", results.error)
            }
        }
    }

    private fun postFavoriteMovies(favoriteMovies: List<MovieUiItemModel>) {
        favoriteMovieListMutableLiveData.postValue(favoriteMovies)
    }
}