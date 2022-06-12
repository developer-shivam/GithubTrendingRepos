package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivamsatija.githubtrendingrepos.GitTrendingReposApplication
import com.shivamsatija.githubtrendingrepos.databinding.ActivityRepositoriesBinding
import com.shivamsatija.githubtrendingrepos.di.component.DaggerActivityComponent
import com.shivamsatija.githubtrendingrepos.di.module.ActivityModule
import com.shivamsatija.githubtrendingrepos.util.handleState
import javax.inject.Inject


class RepositoriesActivity : AppCompatActivity() {

    private val repositoryAdapter by lazy {
        RepositoryAdapter(clickCallback)
    }

    @Inject
    lateinit var repositoriesViewModelFactory: RepositoriesViewModelFactory

    private val repositoriesViewModel: RepositoriesViewModel by viewModels {
        repositoriesViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        val binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRepositories.run {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        repositoriesViewModel.repositoriesLiveData.observe(this) {
            handleState(it, {
                // show loading state
            }, { repositories ->
                repositoryAdapter.setList(repositories)
            }, { error ->
                error?.printStackTrace()
            })
        }

        repositoriesViewModel.currentSelectedPosition.observe(this) {
            repositoryAdapter.updateSelectedItem(it)
        }
    }

    private var clickCallback: (Int) -> Unit = { position ->
        println(position)
        repositoriesViewModel.setSelectedItemPosition(position)
    }

    private fun performDependencyInjection() {
        DaggerActivityComponent.builder()
            .applicationComponent(
                (application as GitTrendingReposApplication).getApplicationComponent()
            )
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}