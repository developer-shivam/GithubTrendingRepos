package com.shivamsatija.githubtrendingrepos

import android.app.Application
import com.shivamsatija.githubtrendingrepos.di.component.ApplicationComponent
import com.shivamsatija.githubtrendingrepos.di.component.DaggerApplicationComponent
import com.shivamsatija.githubtrendingrepos.di.module.ApplicationModule

class GitTrendingReposApplication : Application() {

    private lateinit var _applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        _applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent(): ApplicationComponent {
        return _applicationComponent
    }
}