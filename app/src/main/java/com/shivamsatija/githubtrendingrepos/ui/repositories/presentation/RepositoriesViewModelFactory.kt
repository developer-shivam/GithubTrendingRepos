package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import java.lang.IllegalArgumentException

class RepositoriesViewModelFactory(
    private val repositoryService: RepositoryService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
            return RepositoriesViewModel(repositoryService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}