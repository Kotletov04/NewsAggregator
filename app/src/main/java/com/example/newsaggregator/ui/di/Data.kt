package com.example.newsaggregator.ui.di

import android.content.Context
import androidx.room.Room
import com.example.newsaggregator.data.AppDatabase

import com.example.newsaggregator.data.dao.DataAccessObject
import com.example.newsaggregator.data.repository.NewsRepositoryImplementation
import com.example.newsaggregator.domain.repository.NewsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class Data {

    private val BASE_URL = "https://www.theguardian.com"

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = "room_article.db"
    )
        .createFromAsset("database/room_article.db")
        .build()

    @Provides
    fun provideRetrofitApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                XML.asConverterFactory(
                    "application/xml; charset=UTF8".toMediaType()
                )
            ).build()
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): DataAccessObject {
        return appDatabase.getDao()
    }

    @Provides
    fun provideNewsRepository(retrofit: Retrofit, dao: DataAccessObject): NewsRepository {
        return NewsRepositoryImplementation(retrofit = retrofit, dao = dao)
    }


}