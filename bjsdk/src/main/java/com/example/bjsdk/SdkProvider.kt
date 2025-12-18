package com.example.bjsdk

import android.content.Context
import android.util.Log
import com.example.bjsdk.api.data.remote.ApiService
import com.example.bjsdk.api.data.remote.MovieRepositoryImpl
import com.example.bjsdk.api.domain.repository.MovieRepository
import com.example.bjsdk.data.DatabaseHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.bjsdk.presentation.viewmodel.MainViewModel

/*
object SdkProvider {

    private lateinit var apiService: ApiService
    private lateinit var repository: MovieRepository
    private lateinit var dbHelper: DatabaseHelper
    private var initialized = false

    fun init(context: Context) {
        if (initialized) return

        try {
            Log.d("BJSDKLOG", "BJSDK init() called")
            Log.d("BJSDKLOG", "About to create OkHttpClient")
            val okHttpClient = OkHttpClient.Builder()
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

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            Log.d("BJSDKLOG", "OkHttpClient created")

            apiService = retrofit.create(ApiService::class.java)
            repository = MovieRepositoryImpl(apiService)
            dbHelper = DatabaseHelper(context.applicationContext)

            initialized = true
            Log.d("BJSDKLOG", "SDK initialized successfully")
        } catch (t: Throwable) {
            Log.e("BJSDKLOG", "SDK init failed", t)
        }
    }
}
*/
