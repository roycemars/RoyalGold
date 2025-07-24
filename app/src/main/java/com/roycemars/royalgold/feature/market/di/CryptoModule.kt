package com.roycemars.royalgold.feature.market.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.roycemars.royalgold.BuildConfig
import com.roycemars.royalgold.feature.market.data.local.CryptoDatabase
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.remote.AuthInterceptor
import com.roycemars.royalgold.feature.market.data.remote.CryptoApi
import com.roycemars.royalgold.feature.market.data.remote.CryptoRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {
    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CryptoDatabase::class.java,
            name = "crypto.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // Add your AuthInterceptor here
            .addInterceptor(loggingInterceptor)
            // .connectTimeout(30, TimeUnit.SECONDS) // Optional: set timeouts
            // .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create() // Basic Gson instance
        // You can customize Gson here if needed, e.g.:
        // return GsonBuilder()
        //     .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        //     .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CryptoApi.BASE_URL)
            .client(okHttpClient) // OkHttpClient with the interceptor is used here
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCryptoPager(cryptoDatabase: CryptoDatabase, cryptoApi: CryptoApi): Pager<Int, CryptoEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CryptoRemoteMediator(
                cryptoDatabase = cryptoDatabase,
                cryptoApi = cryptoApi
            ),
            pagingSourceFactory = {
                cryptoDatabase.dao.pagingSource()
            }
        )
    }
}