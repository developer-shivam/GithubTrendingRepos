package com.shivamsatija.githubtrendingrepos.di.module

import android.app.Activity
import android.content.Context
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.ui.repositories.domain.RepositoriesDataManagerImpl
import com.shivamsatija.githubtrendingrepos.di.ActivityContext
import com.shivamsatija.githubtrendingrepos.di.PerActivity
import com.shivamsatija.githubtrendingrepos.ui.repositories.presentation.RepositoriesViewModelFactory
import com.shivamsatija.githubtrendingrepos.util.CoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: Activity
) {

    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    fun provideRepositoriesDataManager(
        repositoriesDataManager: RepositoriesDataManagerImpl
    ): RepositoriesDataManager {
        return repositoriesDataManager
    }

    @Provides
    @PerActivity
    fun provideRepositoryViewModelFactory(
        repositoriesDataManager: RepositoriesDataManager,
        coroutineContextProvider: CoroutineContextProvider
    ): RepositoriesViewModelFactory {
        return RepositoriesViewModelFactory(repositoriesDataManager, coroutineContextProvider)
    }

}