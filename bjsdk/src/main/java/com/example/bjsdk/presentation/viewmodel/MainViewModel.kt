package com.example.bjsdk.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bjsdk.api.domain.repository.MovieRepository
import com.example.bjsdk.data.DatabaseHelper
import com.example.bjsdk.model.DataResponse
import com.example.bjsdk.model.GenreResponse
import com.example.bjsdk.model.Result
import com.example.bjsdk.model.User
import com.example.bjsdk.utils.ScrollingDirection
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val repository: MovieRepository,
    private val dbHelper : DatabaseHelper
) : ViewModel() {
    private val _bannerData = MutableLiveData<DataResponse>()
    val bannerData: LiveData<DataResponse> = _bannerData

    private val _trendingData = MutableLiveData<List<Result>>()
    val trendingData: LiveData<List<Result>> = _trendingData

    private val _upcomingData = MutableLiveData<List<Result>>()
    val upcomingData: LiveData<List<Result>> = _upcomingData

    private val _genreList = MutableLiveData<GenreResponse>()
    val genreList : LiveData<GenreResponse> = _genreList

    private val _user2List = MutableLiveData<List<User>>()
    val user2List : LiveData<List<User>> = _user2List

    private val _scrollingDirection = MutableStateFlow<ScrollingDirection>(ScrollingDirection.NONE)
    val scrollingDirection: StateFlow<ScrollingDirection> = _scrollingDirection.asStateFlow()

    private var _loading = false
    val loading : Boolean = _loading

    var currentEndTrendingPage : Int = 1
    var currentStartTrendingPage : Int = 1
    var currentUpcomingPage : Int = 1
    var pageLimit : Int = 4

    init {
        fetchData()
    }

    private fun fetchData() = runBlocking {
        getUser2List()
        val bannerDeferred = async {
            fetchBannerData()
        }
        val trendingDeferred = async {
            fetchTrendingData(
                page = 1,
                onComplete =  {
                    _loading = false
                }
            )
        }
        val upcomingDeferred = async {
            fetchUpcomingData(page = currentUpcomingPage)
        }
        val genreDeferred = async {
            fetchGenreList()
        }
        awaitAll(bannerDeferred, trendingDeferred, upcomingDeferred, genreDeferred)
    }

    private fun getUser2List(){
        val user2List = dbHelper.getAllUsers2()
        if(user2List.isNotEmpty()){
            _user2List.value = user2List
        }
    }
    suspend fun fetchBannerData(){
        try {
            val bannerData = withContext(Dispatchers.IO) {
                repository.getBannerData()
            }
            _bannerData.value = bannerData
            Log.d("BJSDKLOG", "Banner Data Success = ${bannerData.results}")
        } catch (e: Exception){
            Log.d("BJSDKLOG", "Banner Data Error = ${e.message}")
        }
    }

    suspend fun fetchTrendingData(
        page: Int,
        onComplete: () -> Unit = {},
        onUpdateScrollState: () -> Unit = {},
        isStartDataEnabled : Boolean = false
    ){
        if(page == 0) return
        try {
            if(isStartDataEnabled){
                _loading = true
                currentStartTrendingPage = page
                val data = withContext(Dispatchers.IO) {
                    repository.getTrendingData(page = page)
                }
                val oldData = trendingData.value?.toMutableList() ?: mutableListOf()
                val trimmedData =
                    if (oldData.size > 60){
                        _scrollingDirection.value = ScrollingDirection.LEFT
                        oldData.dropLast(20)
                    }
                    else oldData
                _trendingData.value = data.results + trimmedData
                Log.d("BJSDKLOG", "Trending Data Success = ${data.results}")
                _loading = false
                onComplete()
            } else {
                _loading = true
                currentEndTrendingPage = page
                val data = withContext(Dispatchers.IO) {
                    repository.getTrendingData(page = page)
                }
                val oldData = trendingData.value?.toMutableList() ?: mutableListOf()
                val trimmedData =
                    if (oldData.size > 60){
                        onUpdateScrollState()
                        _scrollingDirection.value = ScrollingDirection.RIGHT
                        oldData.drop(20)
                    }
                    else oldData
                _trendingData.value = trimmedData + data.results
                Log.d("BJSDKLOG", "Trending Data Success = ${data.results}")
                _loading = false
                onComplete()
            }
        } catch (e: Exception){
            Log.d("BJSDKLOG", "Trending Data Error = ${e.message}")
        }
    }

    suspend fun fetchUpcomingData(page: Int){
        try {
            currentUpcomingPage = page
            val data = withContext(Dispatchers.IO) {
                repository.getUpcomingData(page = page)
            }
            val oldData = _upcomingData.value ?: emptyList()
            if(oldData.isNotEmpty()){
                _upcomingData.value = oldData + data.results
            } else {
                _upcomingData.value = data.results
            }
            Log.d("BJSDKLOG", "Upcoming Data Success = ${data.results}")
        } catch (e: Exception){
            Log.d("BJSDKLOG", "Upcoming Data Error = ${e.message}")
        }
    }

    suspend fun fetchGenreList(){
        try {
            val genreList = withContext(Dispatchers.IO) {
                repository.getGenreData()
            }
            _genreList.value = genreList
            Log.d("BJSDKLOG", "Genre List Success = $genreList")
        } catch (e: Exception){
            Log.d("BJSDKLOG", "Genre List Error = ${e.message}")
        }
    }
}