package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var binding: ActivityRepositoriesBinding

    private val repositoriesViewModel: RepositoriesViewModel by viewModels {
        repositoriesViewModelFactory
    }

    private var clickCallback: (Int) -> Unit = { position ->
        repositoriesViewModel.setSelectedItemPosition(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            repositoriesViewModel.fetchRepositories()
            repositoriesViewModel.setSelectedItemPosition(RecyclerView.NO_POSITION)
        }
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

    private fun setupObservers() {
        repositoriesViewModel.repositoriesLiveData.observe(this) {
            handleState(it, {
                binding.swipeRefreshLayout.isRefreshing = true
            }, { repositories ->
                binding.swipeRefreshLayout.isRefreshing = false
                repositoryAdapter.setList(repositories)
            }, { error ->
                binding.swipeRefreshLayout.isRefreshing = true
                error?.printStackTrace()
            })
        }

        repositoriesViewModel.currentSelectedPosition.observe(this) {
            repositoryAdapter.updateSelectedItem(it)
        }
    }

    private fun setupRecyclerView() {
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
    }
}