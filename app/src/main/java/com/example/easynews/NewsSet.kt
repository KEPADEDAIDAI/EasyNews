package com.example.easynews

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsSet (private val newsList: List<Data>) : RecyclerView.Adapter<NewsSet.BaseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            1 -> { //正常新闻
                val itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_news_basic, parent, false)
                NewsViewHolder(itemView)
            }
            else -> {
                //没有新闻了
                val itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.no_more_news, parent, false)
                FinalViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if(holder is NewsViewHolder){ //正常新闻
            val news = newsList[position]
            holder.title.text = news.title //显示新闻标题
            holder.time.text = news.date // 显示新闻时间
            holder.from.text = news.author_name // 显示新闻来源
//            holder.image.setImageResource(R.drawable.apple_pic) //显示图片
            Glide.with(NewsApplication.context).load(news.thumbnail_pic_s).into(holder.image) //从网上获取图片
            holder.itemView.setOnClickListener{//跳转到新闻详细界面
                val intent = Intent(NewsApplication.context, webviewActivity::class.java)
                val currentNews = newsList[holder.adapterPosition]
                intent.putExtra("news_from=", currentNews.author_name)
                intent.putExtra("url=", currentNews.url)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(NewsApplication.context, intent, null)
            }
        }
        else{ //没有新闻了
            val finalViewHolder = holder as FinalViewHolder
            finalViewHolder.message.text = "没有更多的新闻了！"
        }
    }

    override fun getItemCount(): Int = newsList.size + 1
    //所有的新闻和最后一条到底的提示


    override fun getItemViewType(position: Int): Int { //第pos个新闻
        return if (position == itemCount-1) {
            // 最后一个列表项放没有新闻的提示
            999
        } else {
            1
        }
    }
    open inner class FinalViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val message: TextView = itemView.findViewById(R.id.message)
    }
    open inner class NewsViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_news_tv_title) //新闻标题
        val time: TextView = itemView.findViewById(R.id.item_news_tv_time)//发布时间
        val from: TextView = itemView.findViewById(R.id.item_news_tv_source)//来源
        val image: ImageView = itemView.findViewById(R.id.item_news_tv_img) //新闻图片
    }
    open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}