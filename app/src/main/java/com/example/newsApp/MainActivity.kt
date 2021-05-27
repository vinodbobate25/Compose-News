package com.example.newsApp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apps.newsapp.utils.Status
import com.example.newsApp.data.model.ArticlesItem
import com.example.newsApp.news.ListViewModel
import com.example.newsApp.ui.theme.ComposeDemo1Theme
import com.google.accompanist.coil.CoilPainterDefaults
import com.google.accompanist.coil.rememberCoilPainter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
     private val viewModel:ListViewModel by viewModels()
    private var selectedTab:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemo1Theme {
                // A surface container using the 'background' color from the theme
              //  val resource=viewModel.getNews().observeAsState().value
                selectedTab=viewModel.selectedNewsString.value
                NewsHome(viewModel,selectedTab)
            }
        }
    }
}
@Composable
fun NewsHome(viewModel: ListViewModel,selectedTab:String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "News App") },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 8.dp,
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_news),
                            contentDescription = null
                        )
                    }
                }

            )
        },
        content = {
            homeContent(viewModel,selectedTab)
        }
    )
}

@Composable
fun homeContent(viewModel: ListViewModel,selectedTab: String?) {
    Column {
        SearchTabList(viewModel,selectedTab)
        Divider()
        NewsList(model = viewModel)
    }
}

@Composable
fun NewsList(model:ListViewModel) {

val resource=model.getNews().observeAsState()
resource.value?.let {
    it?.let { res->
     when (res.status) {
         Status.SUCCESS -> {
             Log.d("data  exist ", "sdsd")
             val list = it.data
             LazyColumn {
                 itemsIndexed(items = list!!)
                 { index, item ->
                     NewsListItem(article = item)
                 }
             }

         }
         Status.LOADING -> {
             Log.d("data  loading ", "")
             Column(
                 modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
        CircularProgressIndicator()
        }

         }
         Status.ERROR -> {
             Log.d("data  eriir ", "eorroor")
             Column(
                 modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 Text(
                     text = "No updates,Try later",
                     modifier = Modifier.padding(16.dp)
                 )             }
             //Handle Error
             // progressBar.visibility = View.GONE
             //  Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
         }
     }
 }
}

}


@Composable
fun NewsListItem(article:ArticlesItem?) {
    Card(
        elevation = 12.dp,
        border = BorderStroke(2.dp, Color.LightGray),
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = rememberCoilPainter(request = article?.urlToImage.toString()),
            contentDescription = "",
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(all = 10.dp),
            )
            Text(
                text = article?.title.toString(),
                modifier = Modifier.padding(16.dp)
            )
            Divider()
            Text(
                text = article?.description.toString(),
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}

@Composable
fun SearchTabList(viewModel: ListViewModel,selectedTab: String?) {
    val tabs= arrayListOf<String>("Android ","","AI","Lock Down","India")
    LazyRow(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
       items(tabs)
       {
           TagsListItem(viewModel=viewModel,post = it,isSelected = it==selectedTab)
       }
    }
}


@Composable
fun TagsListItem(viewModel: ListViewModel,post: String,isSelected: Boolean=false) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.toggleable(
            value =isSelected,
            onValueChange ={
                    viewModel.selectedNewsString.value = post
                    viewModel.fetchNews(post)

            }

        )) {

        Card(
            elevation = 12.dp,
            border = BorderStroke(1.dp, Color.LightGray),
            backgroundColor=if(isSelected) Color.Yellow else Color.White,
            modifier = Modifier
                .padding(all = 10.dp)
        )
        {
            Text(
                text = post,
                modifier = Modifier.padding(16.dp)

            )
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    ComposeDemo1Theme {
        Greeting("Android")
    }
}