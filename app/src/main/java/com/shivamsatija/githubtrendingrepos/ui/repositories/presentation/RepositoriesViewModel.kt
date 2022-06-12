package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamsatija.githubtrendingrepos.data.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.util.Response
import com.shivamsatija.githubtrendingrepos.util.ViewState
import kotlinx.coroutines.launch

class RepositoriesViewModel(
    private val repositoryDataManager: RepositoriesDataManager
) : ViewModel() {

    val repositoriesLiveData: MutableLiveData<ViewState<List<Repository>>> = MutableLiveData()

    val currentSelectedPosition: MutableLiveData<Int> = MutableLiveData()

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            repositoriesLiveData.value = ViewState.Loading()
            when (val response: Response<List<Repository>> =
                repositoryDataManager.fetchRepositories()
            ) {
                is Response.Success -> {
                    repositoriesLiveData.value = ViewState.Success(response.data)
                }
                is Response.Error -> {
                    repositoriesLiveData.value = ViewState.Error(response.throwable)
                }
            }
        }
    }

    fun setSelectedItemPosition(position: Int) {
        currentSelectedPosition.value = position
    }
}