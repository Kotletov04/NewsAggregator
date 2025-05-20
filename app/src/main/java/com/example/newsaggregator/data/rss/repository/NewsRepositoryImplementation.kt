package com.example.newsaggregator.data.rss.repository

import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.domain.model.NewsModel
import com.example.newsaggregator.domain.repository.NewsRepository
import retrofit2.Retrofit

class NewsRepositoryImplementation(private val retrofit: Retrofit): NewsRepository {

    private val lazyRetrofit by lazy {
        retrofit.create(RssFeed::class.java)
    }

    override suspend fun getNews(): NewsModel {
        val response = lazyRetrofit.getRss()
        val test = response.channel.items[0]
        val testMOdel = NewsModel(
            id = 1,
            title = test.title,
            description = test.title,
            date = test.title,
            image = test.title,
            tag = test.title,
        )
        return testMOdel
    }

}