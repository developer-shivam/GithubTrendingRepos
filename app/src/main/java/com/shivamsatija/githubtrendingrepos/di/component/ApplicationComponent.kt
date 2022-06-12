package com.shivamsatija.githubtrendingrepos.di.component

import android.content.Context
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.di.ApplicationContext
import com.shivamsatija.githubtrendingrepos.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    @ApplicationContext
    fun applicationContext(): Context

    fun repositoryService(): RepositoryService
}