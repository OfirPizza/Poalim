package com.example.poalimtest.connactivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ConnectivityRepoImpl(context: Context) : ConnectivityRepo {

    private var delayLostDisposable: Disposable? = null
    private var connectivityDispatcher = BehaviorSubject.create<ConnectionState>()
    private var lastConnectionState = ConnectionState.CONNECTED


    companion object {
        private const val INTERNET_LOST_DELAY = 4000L
        private val TAG = ConnectivityRepoImpl::class.java.simpleName
    }


    init {
        initConnectivity(context)
    }

    private fun initConnectivity(context: Context) {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.let { manager ->
            checkInitState(manager)
            registerToConnectivity(manager)
        }
    }

    private fun checkInitState(manager: ConnectivityManager) =
        manager.activeNetwork ?: onLostConnection()


    private fun registerToConnectivity(manager: ConnectivityManager) {
        manager.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    delayLostDisposable?.dispose()
                    onGainedConnection()

                }

                override fun onLost(network: Network?) {
                    delayLostDisposable?.dispose()
                    delayLostDisposable = delayDispatchOnLost()
                }

            })
    }

    private fun onLostConnection() {
        lastConnectionState = ConnectionState.LOST
        connectivityDispatcher.onNext(ConnectionState.LOST)
    }

    private fun delayDispatchOnLost() =
        Completable
            .timer(INTERNET_LOST_DELAY, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = { Log.e(TAG, "Failed to dispatch lost connectivity", it) },
                onComplete = { onLostConnection() }
            )

    private fun onGainedConnection() {
        if (lastConnectionState == ConnectionState.CONNECTED) return
        lastConnectionState = ConnectionState.CONNECTED
        connectivityDispatcher.onNext(ConnectionState.CONNECTED)
    }


    enum class ConnectionState {
        LOST,
        CONNECTED
    }


    override fun subscribeToConnectivity(): Flowable<ConnectionState> {
        return connectivityDispatcher.toFlowable(BackpressureStrategy.LATEST)
    }

}