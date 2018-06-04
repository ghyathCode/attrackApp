package com.project.ghyath.attrackkotlin

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val mainWeb = findViewById<WebView>(R.id.main_web)
        mainWeb.setBackgroundColor(Color.TRANSPARENT)
        mainWeb.settings.loadsImagesAutomatically = true
        mainWeb.settings.javaScriptEnabled = true
        mainWeb.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

//        mainWeb.loadUrl(R.string.uri_default.toString())
//        mainWeb.loadUrl("https://www.halabtech.com/")
//        mainWeb.setWebChromeClient(WebChromeClient())
        mainWeb.setWebChromeClient(object:WebChromeClient() {
            override fun onShowFileChooser(webView:WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams:FileChooserParams):Boolean {
                var mFilePathCallback = filePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.setType("*/*")
                val PICKFILE_REQUEST_CODE = 100
                startActivityForResult(intent, PICKFILE_REQUEST_CODE)
                return true
            }
        })
        mainWeb!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mainWeb!!.loadUrl("https://www.attrack.cf/")
//        mainWeb.loadUrl("https://www.attrack.cf")

    }

}
