package com.example.tmdbapp.data.module

import android.content.Context
import androidx.room.Room
import com.example.tmdbapp.data.local.AppDatabase
import com.example.tmdbapp.data.local.dao.ActorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "actors_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideActorDao(database: AppDatabase): ActorDao = database.actorDao()
}