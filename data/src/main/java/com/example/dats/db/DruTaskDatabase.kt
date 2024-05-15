package com.example.dats.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dats.moviedetails.source.local.dao.MovieDetailsDao
import com.example.dats.moviedetails.source.local.model.MovieDetailsLocal
import com.example.dats.movies.source.local.dao.MoviesDao
import com.example.dats.movies.source.local.dao.RemoteKeysDao
import com.example.dats.movies.source.local.model.RemoteKeys
import com.example.dats.movies.source.remote.model.MovieCategory
import com.example.dats.movies.source.remote.model.MovieModel

@Database(
    entities = [MovieModel::class,
        MovieCategory::class,
        RemoteKeys::class,
        MovieDetailsLocal::class],
    version = DruTask_DATABASE_VERSION_NUMBER
)
@TypeConverters(Converters::class)
abstract class DruTaskDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun movieDetailsDao(): MovieDetailsDao

}


const val DB_NAME = "DruTaskDatabase.db"
const val DruTask_DATABASE_VERSION_NUMBER = 4