package com.example.newsaggregator.di

import com.example.newsaggregator.domain.repository.NewsRepository
import com.example.newsaggregator.domain.usecase.NewsUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Domain {


    @Provides
    fun provideNewsUsecase(newsRepository: NewsRepository): NewsUsecase {
        return NewsUsecase(newsRepository = newsRepository)

    }



}