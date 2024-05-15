package com.example.dats.movies.repository

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshManager @AssistedInject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl,
    @Assisted context : Context,
    @Assisted params : WorkerParameters
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        Log.d("TestTestTAG", "doWork: ")
        // Perform your long-running backup operation here.
        moviesRepositoryImpl.loadMovies(categoryId = null)
        // Return SUCCESS if the task was successful.
        // Return FAILURE if the task failed and you don’t want to retry it.
        // Return RETRY if the task failed and you want to retry it.
        return Result.success()
    }
}