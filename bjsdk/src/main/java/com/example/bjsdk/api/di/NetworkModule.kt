package com.example.bjsdk.api.di

import com.example.bjsdk.api.data.remote.ApiService
import com.example.bjsdk.api.data.remote.MovieRepositoryImpl
import com.example.bjsdk.api.domain.repository.MovieRepository
import com.example.bjsdk.data.DatabaseHelper
import com.example.bjsdk.presentation.viewmodel.MainViewModel
import com.example.bjsdk.utils.adapters.MyAdapter
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val sdkModule = module {

    // OkHttp
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMzllZmFhMjdhOGIxOWYxMDg1NDY1N2VjNzdjNjM5YSIsIm5iZiI6MTc1OTMxMzYxOS41OTEsInN1YiI6IjY4ZGNmZWQzMGNlZjcxN2M5MmI0MjYzMyIsInNjb3BlcyI6WyJhcGlfcmFkIl0sInZlcnNpb24iOjF9"
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    // API
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    // Repository
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    // Database
    single {
        DatabaseHelper(androidContext())
    }

    // ViewModel
    viewModel {
        MainViewModel(
            repository = get(),
            dbHelper = get()
        )
    }

    // Adapter (if needed inside SDK)
    factory {
        MyAdapter(get(), get(), get(), get())
    }
}