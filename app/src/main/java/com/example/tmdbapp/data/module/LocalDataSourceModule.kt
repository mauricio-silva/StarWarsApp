package com.example.tmdbapp.data.module

import com.example.tmdbapp.data.local.dao.ActorDao
import com.example.tmdbapp.data.local.datasource.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object LocalDataSourceModule {

    @Provides
    fun provideLocalDataSource(actorDao: ActorDao): LocalDataSource =
        LocalDataSource(actorDao)

}