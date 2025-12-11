package com.example.myapplication.api.data.remote

import com.example.myapplication.api.domain.repository.MovieRepository
import com.example.myapplication.model.DataResponse
import com.example.myapplication.model.GenreResponse

class MovieRepositoryImpl(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getBannerData(): DataResponse {
        val response = apiService.getBannerData()
        try {
            return response
        } catch (e: Exception){
            return response
        }
    }

    override suspend fun getUpcomingData(page: Int): DataResponse {
        val response = apiService.getUpcomingData(page = page)
        try {
            return response
        } catch (e: Exception){
            return response
        }
    }

    override suspend fun getTrendingData(page: Int): DataResponse {
        val response = apiService.getTrendingData(page = page)
        try {
            return response
        } catch (e: Exception){
            return response
        }
    }

    override suspend fun getGenreData(): GenreResponse {
        val response = apiService.getGenreList()
        try {
            return response
        } catch (e: Exception){
            return response
        }
    }
}