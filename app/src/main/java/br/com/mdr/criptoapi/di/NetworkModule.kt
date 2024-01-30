package br.com.mdr.criptoapi.di

import android.util.Log
import br.com.mdr.criptoapi.BuildConfig
import br.com.mdr.criptoapi.common.Constants.BASE_URL
import br.com.mdr.criptoapi.data.local.CriptoDatabase
import br.com.mdr.criptoapi.data.remote.api.CriptoApi
import br.com.mdr.criptoapi.data.repository.RemoteDataSourceImpl
import br.com.mdr.criptoapi.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideCriptoApi(retrofit: Retrofit): CriptoApi =
        retrofit.create(CriptoApi::class.java)

    @Provides
    @Singleton
    fun providesHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            Log.i("OKHTTP", it)
        }
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesAuthorizationInterceptor() = Interceptor { chain ->
        chain.request().run {
            newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("X-CoinAPI-Key", BuildConfig.API_KEY)
                .build()
                .let(chain::proceed)
        }
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        criptoApi: CriptoApi,
        criptoDatabase: CriptoDatabase
    ): RemoteDataSource =
        RemoteDataSourceImpl(
            api = criptoApi,
            dataBase = criptoDatabase
        )

    class AuthorizationInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("X-CoinAPI-Key", BuildConfig.API_KEY)
                .build()

            return chain.proceed(request)
        }
    }
}
