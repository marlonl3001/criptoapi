package br.com.mdr.criptoapi.di

import br.com.mdr.criptoapi.data.repository.ExchangesRepositoryImpl
import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import br.com.mdr.criptoapi.domain.repository.LocalDataSource
import br.com.mdr.criptoapi.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesExchangesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): ExchangesRepository =
        ExchangesRepositoryImpl(remoteDataSource, localDataSource)
}