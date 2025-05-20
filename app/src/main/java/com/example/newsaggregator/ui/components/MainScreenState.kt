package com.example.newsaggregator.ui.components

import com.example.newsaggregator.domain.model.NewsModel

data class MainScreenState(
    val error: String? = "",
    val isLoading: Boolean = false,
    var data: NewsModel? = null
)