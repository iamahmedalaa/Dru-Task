package com.example.drutask.di.module


import com.example.presentaion.base.GlobalState
import com.example.presentaion.base.IGlobalState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalStateModule {

    @Singleton
    @Provides
    fun provideGlobalState() : IGlobalState = GlobalState()
}