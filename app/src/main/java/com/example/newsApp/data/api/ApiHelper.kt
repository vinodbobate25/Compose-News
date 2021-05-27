package com.example.newsApp.data.api

import com.example.newsApp.data.model.ApiNews

interface ApiHelper {
    suspend fun getNews( query:String): ApiNews
}