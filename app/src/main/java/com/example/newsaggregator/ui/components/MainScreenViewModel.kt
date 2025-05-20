package com.example.newsaggregator.ui.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.domain.common.Resource
import com.example.newsaggregator.domain.usecase.NewsUsecase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val newsUsecase: NewsUsecase
): ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    fun getNews() {
        newsUsecase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = MainScreenState(data = result.data)
                }

                is Resource.Error -> {
                    _state.value = MainScreenState(error = result.message)
                }

                is Resource.Loading -> {
                    _state.value = MainScreenState(isLoading = true)
                }
            }


    }.launchIn(viewModelScope)

    }

}