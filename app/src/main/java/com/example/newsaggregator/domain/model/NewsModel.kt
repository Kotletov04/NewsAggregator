package com.example.newsaggregator.domain.model

data class NewsModel(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val image: String,
    val tag: String
)
