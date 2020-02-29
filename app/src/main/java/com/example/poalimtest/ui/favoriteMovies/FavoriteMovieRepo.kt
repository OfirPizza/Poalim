package com.example.poalimtest.ui.favoriteMovies

import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.Result

interface FavoriteMovieRepo {
    suspend fun getFavoriteMovieList(): Result<Exception, List<MovieUiItemModel>>
}


