package com.semba.dkbimages.core.di

import com.semba.dkbimages.core.ConnectivityNetworkMonitor
import com.semba.dkbimages.core.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConnectivityModule {

    @Binds
    fun bindsConnectivityManager(networkMonitor: ConnectivityNetworkMonitor): NetworkMonitor
}