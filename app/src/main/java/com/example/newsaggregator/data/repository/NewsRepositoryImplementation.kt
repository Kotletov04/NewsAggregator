package com.example.newsaggregator.data.repository

import com.example.newsaggregator.data.dao.DataAccessObject
import com.example.newsaggregator.data.entities.ItemEntity
import com.example.newsaggregator.data.entities.toItemModel
import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.data.rss.dto.toItemModel
import com.example.newsaggregator.domain.model.ItemModel
import com.example.newsaggregator.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class NewsRepositoryImplementation(
    private val retrofit: Retrofit,
    private val dao: DataAccessObject): NewsRepository {

    private val lazyRetrofit by lazy {
        retrofit.create(RssFeed::class.java)
    }

    override suspend fun getNews(): List<ItemModel> {
        val response = lazyRetrofit.getRss()
        return response.channel.items.map { it.toItemModel() }
    }

    override suspend fun getNewsInDatabase(): List<ItemModel> {
        return withContext(Dispatchers.IO) {
            dao.getAllNews().map { it.toItemModel() }
        }

    }

    override suspend fun saveNewsInDatabase(data: List<ItemModel>) {
        withContext(Dispatchers.IO) {
            dao.insertNews(data = data.map { ItemEntity(
                title = it.title,
                link = it.link,
                description = it.description,
                categories = it.categories,
                pubDate = it.pubDate,
                guid = it.guid,
                contents = it.contents,
                dcCreator = it.dcCreator
            ) })
            }
        }


    }

