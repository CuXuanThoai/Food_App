package com.example.foody.data

import com.example.foody.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    remoteLocalDataSource: LocalDataSource
) {
    val remote  = remoteDataSource
    val local = remoteLocalDataSource
}