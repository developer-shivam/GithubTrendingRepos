package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamsatija.githubtrendingrepos.data.model.Repository
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoriesViewModel(
    private val repositoryService: RepositoryService
) : ViewModel() {

    val repositoriesLiveData: MutableLiveData<List<Repository>> = MutableLiveData()

    val currentSelectedPosition: MutableLiveData<Int> = MutableLiveData()

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            val repositories = withContext(Dispatchers.IO) {
                repositoryService.fetchRepositories()
            }
            repositoriesLiveData.postValue(repositories)
        }
    }

    fun setSelectedItemPosition(position: Int) {
        currentSelectedPosition.value = position
    }
}