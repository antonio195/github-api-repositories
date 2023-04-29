package com.antoniocostadossantos.githubapirepositories.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antoniocostadossantos.githubapirepositories.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url: String = intent.getStringExtra("URL")!!
        val webview = binding.webview
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(url)

    }
}