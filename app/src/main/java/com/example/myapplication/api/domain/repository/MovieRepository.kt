package com.example.myapplication.api.domain.repository

import com.example.myapplication.model.DataResponse
import com.example.myapplication.model.GenreResponse

interface MovieRepository {
    suspend fun getBannerData(): DataResponse
    suspend fun getUpcomingData(page: Int): DataResponse
    suspend fun getTrendingData(page: Int): DataResponse
    suspend fun getGenreData(): GenreResponse
}