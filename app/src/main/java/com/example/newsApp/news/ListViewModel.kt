package com.example.newsApp.news

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.newsapp.data.NewsRepository
import com.apps.newsapp.utils.Resource
import com.example.newsApp.data.model.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val newsRepository: NewsRepository):ViewModel(){
    private val listlivedata=MutableLiveData<Resource<List<ArticlesItem>>>()
    var selectedNewsString: MutableState<String?> = mutableStateOf("Global warming")
    init {
        fetchNews(selectedNewsString.value)
    }

      fun fetchNews(selectedTab:String?)
    {
        Log.d("user   fetching ",""+selectedTab)
        selectedNewsString.value=selectedTab
        viewModelScope.launch {
            Log.d("selected tab",selectedTab!!)
            listlivedata.postValue(Resource.loading(null))
            try {
                
            val newsFromAPI=newsRepository.getNews(selectedTab!!)
                Log.d("data  fetched ",newsFromAPI.status.toString())

                listlivedata.postValue(Resource.success(newsFromAPI.articles!!))
            }
            catch (e:Exception)
            {
                Log.d("data  erroor occuress ",e.message.toString())

                listlivedata.postValue(Resource.error("ERROR ouccred "+e.printStackTrace(),null))
            }
        }
    }

    fun getNews():LiveData<Resource<List<ArticlesItem>>>
    {
        return  listlivedata
    }
}