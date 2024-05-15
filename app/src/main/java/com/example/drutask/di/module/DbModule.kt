package com.example.drutask.di.module

import android.content.Context
import androidx.room.Room
import com.example.dats.db.DB_NAME
import com.example.dats.db.DruTaskDatabase
import com.example.dats.moviedetails.source.local.dao.MovieDetailsDao
import com.example.dats.movies.source.local.dao.MoviesDao
import com.example.dats.movies.source.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DruTaskDatabase {
        return Room.databaseBuilder(context, DruTaskDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(db:DruTaskDatabase): MoviesDao {
        return db.moviesDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(db:DruTaskDatabase): RemoteKeysDao {
        return db.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailsDao(db:DruTaskDatabase): MovieDetailsDao {
        return db.movieDetailsDao()
    }

}