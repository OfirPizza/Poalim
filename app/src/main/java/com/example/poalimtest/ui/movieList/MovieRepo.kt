package com.example.poalimtest.ui.movieList

import com.example.poalimtest.ui.movieList.model.MovieListUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.Result

interface MovieRepo {
    suspend fun getMovieList(pageNumber: Int): Result<Exception, MovieListUiModel>
    suspend fun addMovieToFavorites(movieUiItemModel: MovieUiItemModel): Result<Exception, Unit>
    suspend fun removeMovieFromFavorites(movieUiItemModel: MovieUiItemModel): Result<Exception, Unit>
}


