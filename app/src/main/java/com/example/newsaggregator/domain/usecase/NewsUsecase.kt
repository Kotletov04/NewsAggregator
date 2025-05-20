package com.example.newsaggregator.domain.usecase

import com.example.newsaggregator.domain.common.Resource
import com.example.newsaggregator.domain.model.NewsModel
import com.example.newsaggregator.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsUsecase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<NewsModel>> = flow {

        try {
            emit(Resource.Loading())
            val result = newsRepository.getNews()
            emit(Resource.Success(data = result))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Ошибка сети"))
        }

    }
}