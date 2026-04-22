package com.soma2026.lab.layered.data.di

import com.soma2026.lab.layered.data.repository.SampleRepositoryImpl
import com.soma2026.lab.layered.data.repository.StandingsRepositoryImpl
import com.soma2026.lab.layered.domain.repository.SampleRepository
import com.soma2026.lab.layered.domain.repository.StandingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSampleRepository(
        impl: SampleRepositoryImpl,
    ): SampleRepository

    @Binds
    @Singleton
    abstract fun bindStandingsRepository(
        impl: StandingsRepositoryImpl,
    ): StandingsRepository
}
