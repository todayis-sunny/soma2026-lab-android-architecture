package com.soma2026.lab.clean.data.di

import com.soma2026.lab.clean.data.repository.SampleRepositoryImpl
import com.soma2026.lab.clean.domain.repository.SampleRepository
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
}
