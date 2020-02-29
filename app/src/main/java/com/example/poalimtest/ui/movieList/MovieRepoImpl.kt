package com.example.poalimtest.ui.movieList

import com.example.poalimtest.dataBase.MovieDao
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity
import com.example.poalimtest.network.MovieItemResponse
import com.example.poalimtest.network.MovieResponse
import com.example.poalimtest.network.NetworkApi
import com.example.poalimtest.ui.movieList.model.ImageUiModel
import com.example.poalimtest.ui.movieList.model.MovieListUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.AppConstance.DEFAULT_DATE
import com.example.poalimtest.util.AppConstance.DEFAULT_DESCRIPTION
import com.example.poalimtest.util.AppConstance.DEFAULT_PAGE_NUMBER
import com.example.poalimtest.util.AppConstance.DEFAULT_RATING
import com.example.poalimtest.util.AppConstance.DEFAULT_TITLE
import com.example.poalimtest.util.AppConstance.DEFAULT_TOTAL_PAGE_NUMBER
import com.example.poalimtest.util.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

class MovieRepoImpl(
    private val service: NetworkApi,
    private val movieDbRepo: MovieDao
) : MovieRepo {

    override suspend fun getMovieList(pageNumber: Int): Result<Exception,MovieListUiModel> =
        Result.build { service.getMovieList(pageNumber = pageNumber).toMovieListUiModel}


    override suspend fun addMovieToFavorites(movieUiItemModel: MovieUiItemModel): Result<Exception,Unit> =
        Result.build {  movieDbRepo.insertOrUpdate(movieUiItemModel.toFavoriteMovieEntity)}

    override suspend fun removeMovieFromFavorites(movieUiItemModel: MovieUiItemModel): Result<Exception,Unit> =
        Result.build { movieDbRepo.removeFavoriteMovie(movieUiItemModel.toFavoriteMovieEntity)}


    private val MovieUiItemModel.toFavoriteMovieEntity: FavoriteMovieEntity
        get() = FavoriteMovieEntity(
            id = this.id,
            imageUrl = this.movieImage.url,
            title = this.title,
            rating = this.rating,
            description = this.description,
            date = this.date,
            isFavorite = true
        )

    private val MovieItemResponse.toMovieUiItem: MovieUiItemModel
        get() = MovieUiItemModel(
            id = this.id,
            movieImage = ImageUiModel(BASE_IMAGE_URL + poster),
            title = title ?: DEFAULT_TITLE,
            rating = rating ?: DEFAULT_RATING,
            description = description ?: DEFAULT_DESCRIPTION,
            date = date ?: DEFAULT_DATE,
            isFavorite = runBlocking(IO){ getIsFavorite(id) }
        )

    private suspend fun getIsFavorite(id: String): Boolean {
      return  movieDbRepo.isMovieFavorite(id)
     }


    private val MovieResponse.toMovieListUiModel: MovieListUiModel
        get() = MovieListUiModel(
            movieList = this.movieList?.map { it.toMovieUiItem }?: emptyList(),
            currentPage = this.pageNumber?: DEFAULT_PAGE_NUMBER,
            totalPages = this.totalPages?: DEFAULT_TOTAL_PAGE_NUMBER
        )
}