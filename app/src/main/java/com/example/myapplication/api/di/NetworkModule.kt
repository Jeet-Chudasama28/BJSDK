package com.example.myapplication.api.di

import com.example.myapplication.api.data.remote.ApiService
import com.example.myapplication.api.data.remote.MovieRepositoryImpl
import com.example.myapplication.api.domain.repository.MovieRepository
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.utils.adapters.MyAdapter
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMzllZmFhMjdhOGIxOWYxMDg1NDY1N2VjNzdjNjM5YSIsIm5iZiI6MTc1OTMxMzYxOS41OTEsInN1YiI6IjY4ZGNmZWQzMGNlZjcxN2M5MmI0MjYzMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.U1ZnEKK7RH9npz5aVvQR8n6tH8dPM_EhusCSNFlzZ6w") // common header
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    single {
        MyAdapter(
            get(), get(), get(), get()
        )
    }

    viewModel {
        MainViewModel(get(), get())
    }

    single {
        DatabaseHelper(get())
    }

}