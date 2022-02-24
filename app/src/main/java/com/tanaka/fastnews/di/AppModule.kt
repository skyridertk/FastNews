package com.tanaka.fastnews.di

import android.content.Context
import androidx.room.Room
import com.tanaka.fastnews.data.api.NewsAPI
import com.tanaka.fastnews.data.database.NewsDatabase
import com.tanaka.fastnews.data.datastore.UserPreferences
import com.tanaka.fastnews.repository.NewsRepository
import com.tanaka.fastnews.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tanaka.fastnews.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesNewsAPI(): NewsAPI {
        val httpLogger = HttpLoggingInterceptor()
            .apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

        val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogger)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(NewsAPI::class.java)
    }



    @Singleton
    @Provides
    fun providesUserPreferences(@ApplicationContext context: Context) = UserPreferences(context)

    @Singleton
    @Provides
    fun providesNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase = Room.databaseBuilder(context, NewsDatabase::class.java, "news-db").build()


    @Singleton
    @Provides
    fun providesNewsRepository(
        newsAPI: NewsAPI,
        newsDatabase: NewsDatabase
    ): NewsRepository = NewsRepositoryImpl(newsAPI, newsDatabase.newsDao())
}
