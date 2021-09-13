package com.example.easynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val newsList = ArrayList<Data>() //新闻
    private val adapter = NewsSet(newsList)
    private lateinit var newsRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        //设定单列
        newsRecyclerView = recyclerView //新闻列表框
        newsRecyclerView.layoutManager = layoutManager
        newsRecyclerView.adapter = adapter
        getDataFromNetwork()//获得新闻数据
    }
    private fun getDataFromNetwork(){
        thread{//耗时行为，开子进程
            var list: List<Data>? = null
            try{
                val key = "d8315ea8e36cdc67ffd06d51eb43a913"
                val URL = "https://v.juhe.cn/toutiao/index?type=top&key=$key"
                val request =
                    Request.Builder()
                        .url(URL)
                        .build()
                val response = OkHttpClient().newCall(request).execute()//发送请求
                val json = response.body?.string()
                print(json)
                val newsResponse = Gson().fromJson(json, NewsBeen::class.java) //将json文件转为可用的类
                if(newsResponse != null)
                {
                    when (newsResponse.error_code){
                        0->{
                            try {
                                //错误码 0代表无错
                                list = newsResponse.result.data
                                updateInRecyclerView(list) //更新UI
                            }catch (e: Exception){
                                e.printStackTrace()
                            }

                        }
                    }
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    private fun updateInRecyclerView(newsData: List<Data>) {
        runOnUiThread{//UI线程中更新
            try {
                newsList.clear()//清空
                newsList.addAll(newsData)//重放
                adapter.notifyDataSetChanged()//自动更新
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}