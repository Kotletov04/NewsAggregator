package com.example.newsaggregator.ui.components

import com.example.newsaggregator.domain.model.ItemModel

data class MainScreenState(
    val error: String? = "",
    val isLoading: Boolean = false,
    var data: List<ItemModel>? = null
)