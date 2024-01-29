package br.com.mdr.criptoapi.di

import br.com.mdr.criptoapi.domain.repository.ExchangesRepository
import br.com.mdr.criptoapi.domain.usecase.ExchangeDetailUseCase
import br.com.mdr.criptoapi.domain.usecase.ExchangesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideHeroesUseCase(repository: ExchangesRepository): ExchangesUseCase {
        return ExchangesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesExchangeDetailUseCase(repository: ExchangesRepository): ExchangeDetailUseCase {
        return ExchangeDetailUseCase(repository)
    }
}