package com.example.poalimtest.ui.movieList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poalimtest.connactivity.ConnectivityRepo
import com.example.poalimtest.connactivity.ConnectivityRepoImpl
import com.example.poalimtest.ui.movieList.model.MovieListUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import com.example.poalimtest.util.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private val TAG = MovieViewModel::class.java.simpleName

class MovieViewModel(
    private val movieRepo: MovieRepo,
    private val connectivityRepo: ConnectivityRepo
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var pageNumber = 1
    private var totalPageAvailable = 1

    private val movieListMutableLiveData = MutableLiveData<MovieListUiModel>()
    val movieListLiveData: LiveData<MovieListUiModel> = movieListMutableLiveData

    private val refreshUiMutableLiveData = MutableLiveData<Unit>()
    val refreshUiLiveData: LiveData<Unit> = refreshUiMutableLiveData

    private val isNetworkAvailableMutableLiveData = MutableLiveData<ConnectivityRepoImpl.ConnectionState>()
    val isNetworkAvailableLiveData: LiveData<ConnectivityRepoImpl.ConnectionState> = isNetworkAvailableMutableLiveData


    init {
        getMovieList()
        subscribeToNetworkStatus()
    }

    fun getMovieList() {
        getMovieList(pageNumber)
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun getMovieList(pageNumber: Int) {
        CoroutineScope(IO).launch {
            val result = movieRepo.getMovieList(pageNumber)
            when (result) {
                is Result.Value -> {
                    updateTotalPagesAvailable(result.value.totalPages)
                    postMovieList(result.value)
                }
                is Result.Error -> Log.e(TAG, "Failed to get movie list", result.error)
            }
        }
    }

    private fun updateTotalPagesAvailable(totalPages: Int) {
        totalPageAvailable = totalPages
    }

    private fun postMovieList(movieListUiModel: MovieListUiModel) {
        movieListMutableLiveData.postValue(movieListUiModel)
    }

    private fun postRefreshUi() {
        refreshUiMutableLiveData.postValue(Unit)
    }

    private fun postConnectivityState(connectionState: ConnectivityRepoImpl.ConnectionState) {
        isNetworkAvailableMutableLiveData.postValue(connectionState)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun loadNextPage() {
        if (pageNumber == totalPageAvailable) return
        getMovieList(++pageNumber)
    }


    private fun subscribeToNetworkStatus() {
        connectivityRepo.subscribeToConnectivity().subscribeOn(Schedulers.io())
            .doOnNext { postConnectivityState(it) }
            .subscribeBy(onError = { Log.e(TAG, "Failed to subscribe to network", it) })
            .addTo(compositeDisposable)
    }

    fun updateFavoriteMovie(movieItem: MovieUiItemModel) {
        if (movieItem.isFavorite) {
            removeFavoriteMovie(movieItem)
            return
        }
        addFavoriteMovie(movieItem)
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun addFavoriteMovie(movieItem: MovieUiItemModel) {
        CoroutineScope(IO).launch {
            val result = movieRepo.addMovieToFavorites(movieItem)
            when (result) {
                is Result.Value -> {
                    Log.d(TAG, "Saved favorite movie ID: ${movieItem.id}")
                    movieItem.isFavorite = true
                    postRefreshUi()
                }
                is Result.Error -> Log.e(TAG, "Failed to save  favorite movie", result.error)
            }
        }
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun removeFavoriteMovie(movieItem: MovieUiItemModel) {
        CoroutineScope(IO).launch {
            val result = movieRepo.removeMovieFromFavorites(movieItem)
            when (result) {
                is Result.Value -> {
                    Log.d(TAG, "Removed favorite movie ID: ${movieItem.id}")
                    movieItem.isFavorite = false
                    postRefreshUi()
                }
                is Result.Error -> Log.e(TAG, "Failed to remove movie from favorite list", result.error)
            }
        }
    }
}