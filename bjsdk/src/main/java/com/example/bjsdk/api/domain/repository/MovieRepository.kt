package com.example.bjsdk.api.domain.repository

import com.example.bjsdk.model.DataResponse
import com.example.bjsdk.model.GenreResponse

interface MovieRepository {
    suspend fun getBannerData(): DataResponse
    suspend fun getUpcomingData(page: Int): DataResponse
    suspend fun getTrendingData(page: Int): DataResponse
    suspend fun getGenreData(): GenreResponse
}