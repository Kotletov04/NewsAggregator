package com.example.newsaggregator.domain.repository

import com.example.newsaggregator.domain.model.ItemModel

interface NewsRepository {

    suspend fun getNews(): List<ItemModel>

    suspend fun getNewsInDatabase(): List<ItemModel>

    suspend fun saveNewsInDatabase(data: List<ItemModel>)
}