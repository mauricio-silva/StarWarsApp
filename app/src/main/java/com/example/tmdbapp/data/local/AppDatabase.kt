package com.example.tmdbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdbapp.data.local.dao.ActorDao
import com.example.tmdbapp.data.local.entity.ActorEntity
import com.example.tmdbapp.data.local.entity.ActorMovieEntity

@Database(entities = [ActorEntity::class, ActorMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actorDao(): ActorDao
}
