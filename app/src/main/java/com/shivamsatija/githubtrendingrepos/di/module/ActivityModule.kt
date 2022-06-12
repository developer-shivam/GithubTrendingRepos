package com.shivamsatija.githubtrendingrepos.di.module

import android.app.Activity
import android.content.Context
import com.shivamsatija.githubtrendingrepos.data.RepositoriesDataManager
import com.shivamsatija.githubtrendingrepos.data.RepositoriesDataManagerImpl
import com.shivamsatija.githubtrendingrepos.di.ActivityContext
import com.shivamsatija.githubtrendingrepos.di.PerActivity
import com.shivamsatija.githubtrendingrepos.ui.repositories.presentation.RepositoriesViewModelFactory
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
        repositoriesDataManager: RepositoriesDataManager
    ): RepositoriesViewModelFactory {
        return RepositoriesViewModelFactory(repositoriesDataManager)
    }

}