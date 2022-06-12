package com.shivamsatija.githubtrendingrepos.di.component

import com.shivamsatija.githubtrendingrepos.di.PerActivity
import com.shivamsatija.githubtrendingrepos.di.module.ActivityModule
import com.shivamsatija.githubtrendingrepos.ui.repositories.presentation.RepositoriesActivity
import dagger.Component

@PerActivity
@Component(
    modules = [ActivityModule::class],
    dependencies = [ApplicationComponent::class]
)
interface ActivityComponent {

    fun inject(repositoriesActivity: RepositoriesActivity)
}