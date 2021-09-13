package com.example.easynews

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class webviewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        val webView = findViewById<WebView>(R.id.news_web_view)
        val url = intent.getStringExtra("url=")
        if (url != null) {
            webView.loadUrl(url)
        }//打开新闻网页
    }
}