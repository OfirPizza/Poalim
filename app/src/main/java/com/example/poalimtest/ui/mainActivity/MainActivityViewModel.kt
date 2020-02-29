package com.example.poalimtest.ui.mainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poalimtest.connactivity.ConnectivityRepo
import com.example.poalimtest.connactivity.ConnectivityRepoImpl.ConnectionState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


private val TAG = MainActivityViewModel::class.java.simpleName

class MainActivityViewModel(private val connectivityRepo: ConnectivityRepo) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val isNetworkAvailableMutableLiveData = MutableLiveData<ConnectionState>()
    val isNetworkAvailableLiveData: LiveData<ConnectionState> = isNetworkAvailableMutableLiveData

    init {
        subscribeToNetworkStatus()
    }

    private fun subscribeToNetworkStatus() {
        connectivityRepo.subscribeToConnectivity().subscribeOn(Schedulers.io())
            .doOnNext { postConnectivityState(it) }
            .subscribeBy(onError = { Log.e(TAG, "Failed to subscribe to network", it) })
            .addTo(compositeDisposable)
    }

    private fun postConnectivityState(connectionState: ConnectionState) {
        isNetworkAvailableMutableLiveData.postValue(connectionState)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}