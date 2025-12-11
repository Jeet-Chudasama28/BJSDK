package com.example.myapplication.api.data.remote

import com.example.myapplication.model.DataResponse
import com.example.myapplication.model.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getBannerData(): DataResponse

    @GET("movie/popular")
    suspend fun getTrendingData(@Query("page")page: Int): DataResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingData(@Query("page")page: Int): DataResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreResponse

}