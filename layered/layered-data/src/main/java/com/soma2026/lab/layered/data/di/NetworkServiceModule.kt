package com.soma2026.lab.layered.data.di

import com.soma2026.lab.layered.data.api.PremierLeagueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun providePremierLeagueApiService(retrofit: Retrofit): PremierLeagueApiService =
        retrofit.create(PremierLeagueApiService::class.java)
}
