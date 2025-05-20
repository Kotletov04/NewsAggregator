package com.example.newsaggregator.domain.repository

import com.example.newsaggregator.domain.model.NewsModel

interface NewsRepository {

    suspend fun getNews(): NewsModel

}