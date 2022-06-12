package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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

    private var clickCallback: (String) -> Unit = { position ->
        repositoriesViewModel.setSelectedItemId(position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupRecyclerView()
        setupSearch()

        binding.swipeRefreshLayout.setOnRefreshListener {
            repositoriesViewModel.fetchRepositories()
            repositoriesViewModel.setSelectedItemId()
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

        repositoriesViewModel.currentSelectedPositionLiveData.observe(this) {
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

    private fun setupSearch() {
        binding.etSearch
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    query: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (!TextUtils.isEmpty(query)) {
                        if (query!!.length > 1) {
                            repositoriesViewModel.searchLocal(query.toString())
                        }
                    } else {
                        repositoriesViewModel.searchLocal(query.toString())
                    }
                }
            })
    }
}