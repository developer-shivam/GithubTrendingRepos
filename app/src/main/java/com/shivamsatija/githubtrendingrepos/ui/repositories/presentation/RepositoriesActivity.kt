package com.shivamsatija.githubtrendingrepos.ui.repositories.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivamsatija.githubtrendingrepos.data.remote.RepositoryService
import com.shivamsatija.githubtrendingrepos.databinding.ActivityRepositoriesBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepositoriesActivity : AppCompatActivity() {

    private lateinit var repositoryService: RepositoryService

    private val repositoryAdapter by lazy {
        RepositoryAdapter(clickCallback)
    }

    private val repositoriesViewModel: RepositoriesViewModel by viewModels {
        RepositoriesViewModelFactory(repositoryService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createDependencies()

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
            repositoryAdapter.setList(it)
        }

        repositoriesViewModel.currentSelectedPosition.observe(this) {
            repositoryAdapter.updateSelectedItem(it)
        }
    }

    private var clickCallback: (Int) -> Unit = { position ->
        println(position)
        repositoriesViewModel.setSelectedItemPosition(position)
    }

    private fun createDependencies() {
        val loggingInterceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BASIC
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gh-trending-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        repositoryService = retrofit.create(RepositoryService::class.java)
    }
}