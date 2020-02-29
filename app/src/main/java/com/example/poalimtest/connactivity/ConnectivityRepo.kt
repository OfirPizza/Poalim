package com.example.poalimtest.connactivity

import io.reactivex.Flowable

interface ConnectivityRepo {
    fun subscribeToConnectivity(): Flowable<ConnectivityRepoImpl.ConnectionState>
}