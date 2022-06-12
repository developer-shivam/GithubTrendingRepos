package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.util.CoroutineContextProvider

class RepositoriesViewModelFactory(
    private val repositoryDataManager: RepositoriesDataManager,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
            return RepositoriesViewModel(repositoryDataManager, coroutineContextProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}