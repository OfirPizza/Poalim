package com.example.poalimtest.ui.favoriteMovies

import com.example.poalimtest.dataBase.MovieDao
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity
import com.example.poalimtest.ui.movieList.model.ImageUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.Result

class FavoriteMovieRepoImpl(
    private val movieDbRepo: MovieDao
) : FavoriteMovieRepo {

    override suspend fun getFavoriteMovieList(): Result<Exception, List<MovieUiItemModel>> =
        Result.build { movieDbRepo.getFavoriteMovies().map { it.toFavoriteMovies } }

    private val FavoriteMovieEntity.toFavoriteMovies: MovieUiItemModel
        get() = MovieUiItemModel(
            id = this.id,
            movieImage = ImageUiModel(this.imageUrl),
            title = this.title,
            rating = this.rating,
            description = this.description,
            date = this.date,
            isFavorite = this.isFavorite
        )
}