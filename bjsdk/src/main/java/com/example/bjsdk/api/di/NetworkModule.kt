package com.example.bjsdk.api.di

import com.example.bjsdk.api.data.remote.ApiService
import com.example.bjsdk.api.data.remote.MovieRepositoryImpl
import com.example.bjsdk.api.domain.repository.MovieRepository
import com.example.bjsdk.data.DatabaseHelper
import com.example.bjsdk.presentation.viewmodel.MainViewModel
import com.example.bjsdk.utils.adapters.MyAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
