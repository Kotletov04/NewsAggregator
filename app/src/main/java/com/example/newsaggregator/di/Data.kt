package com.example.newsaggregator.di

import com.example.newsaggregator.data.rss.repository.NewsRepositoryImplementation
import com.example.newsaggregator.domain.repository.NewsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideNewsRepository(retrofit: Retrofit): NewsRepository {
        return NewsRepositoryImplementation(retrofit = retrofit)
    }


}