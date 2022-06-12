package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.util.Response
import com.shivamsatija.githubtrendingrepos.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RepositoriesViewModel(
    private val repositoryDataManager: RepositoriesDataManager
) : ViewModel() {

    val repositoriesLiveData: MutableLiveData<ViewState<List<Repository>>> = MutableLiveData()
    val currentSelectedPositionLiveData: MutableLiveData<String?> = MutableLiveData()

    private var originalRepositories = ArrayList<Repository>()

    private var searchJob: Job? = null

    init {
        fetchRepositories()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            repositoriesLiveData.value = ViewState.Loading()
            when (val response: Response<List<Repository>> =
                repositoryDataManager.fetchRepositories()
            ) {
                is Response.Success -> {
                    originalRepositories = ArrayList(response.data)
                    repositoriesLiveData.value = ViewState.Success(response.data)
                }
                is Response.Error -> {
                    repositoriesLiveData.value = ViewState.Error(response.throwable)
                }
            }
        }
    }

    fun setSelectedItemId(id: String? = null) {
        currentSelectedPositionLiveData.value = id
    }

    fun searchLocal(searchQuery: String? = null) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchQuery?.let {
                delay(300)
                repositoriesLiveData.postValue(ViewState.Success(
                    originalRepositories.filter {
                        it.toString().contains(searchQuery, true)
                    }
                ))
            } ?: run {
                if (originalRepositories.isEmpty()) {
                    fetchRepositories()
                } else {
                    repositoriesLiveData.postValue(
                        ViewState.Success(originalRepositories)
                    )
                }
            }
        }
    }
}