package com.apps.newsapp.data

import com.example.newsApp.data.api.ApiHelper
import javax.inject.Inject

class NewsRepository @Inject constructor(private  val apiHelper: ApiHelper) {
    suspend fun getNews(query:String)=apiHelper.getNews(query=query)
}