package br.com.mdr.criptoapi.di

import android.content.Context
import androidx.room.Room
import br.com.mdr.criptoapi.common.Constants.CRIPTO_DATABASE
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.data.repository.LocalDataSourceImpl
import br.com.mdr.criptoapi.domain.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // Provides a singleton database instance
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CriptoDatabase =
        Room.databaseBuilder(
            context = context,
            klass = CriptoDatabase::class.java,
            name = CRIPTO_DATABASE
        ).build()

    @Provides
    @Singleton
    fun providesLocalDataSource(dataBase: CriptoDatabase): LocalDataSource {
        return LocalDataSourceImpl(database = dataBase)
    }
}
