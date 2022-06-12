package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager

class RepositoriesViewModelFactory(
    private val repositoryDataManager: RepositoriesDataManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
            return RepositoriesViewModel(repositoryDataManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}