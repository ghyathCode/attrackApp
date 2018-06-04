package com.project.ghyath.attrackkotlin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mainWeb : WebView
    var photoURI : Uri? = null

    val CAMERA_REQUEST_CODE = 0
    lateinit var imageFilePath: String

    var parentLayout : View? = null

    var mfilePathCallback: ValueCallback<Array<Uri>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        parentLayout = findViewById<View>(R.id.parent_layout)

        mainWeb = findViewById<WebView>(R.id.main_web)
        mainWeb.setBackgroundColor(Color.TRANSPARENT)
        mainWeb.settings.loadsImagesAutomatically = true
        mainWeb.settings.javaScriptEnabled = true
        mainWeb.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY


//        mainWeb.setWebChromeClient(object: WebChromeClient() {
//            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams):Boolean {
//                var mFilePathCallback = filePathCallback
//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.setType("*/*")
//                val PICKFILE_REQUEST_CODE = 100
//                startActivityForResult(intent, PICKFILE_REQUEST_CODE)
//                return true
//            }
//        })


        mainWeb.webChromeClient = object: WebChromeClient() {
            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams):Boolean {
//                try {
//                    val imageFile = createTemporaryFile("picture", ".jpg")
//                    val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    if(callCameraIntent.resolveActivity(packageManager) != null) {
//                        val authorities = packageName + ".fileprovider"
//                        val imageUri = FileProvider.getUriForFile(applicationContext, authorities, imageFile)
//                        callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//                        startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
//                    }

//                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    val imageFile = createImageFile()
//                    photoURI = Uri.fromFile(imageFile)
//                    Toast.makeText(this@MainActivity, "photoURI"+photoURI.toString(), Toast.LENGTH_SHORT).show()
//                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
//                } catch (e: IOException) {
//                    Toast.makeText(this@MainActivity,"FROM HERE \n Could not create file",Toast.LENGTH_LONG).show()
//                }

                mfilePathCallback = filePathCallback
                OnCameraOpenPressed()
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);

                return true
            }
        }


            mainWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mainWeb.loadUrl("https://up.halabtech.com")
//        mainWeb.loadUrl("https://www.attrack.cf")



        fab.setOnClickListener { view ->
//            mainWeb.loadUrl("https://www.attrack.cf/")
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Action", null).show()
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val imageFile = createImageFile()
                photoURI = Uri.fromFile(imageFile)
                Toast.makeText(this, "photoURI"+photoURI.toString(), Toast.LENGTH_SHORT).show()
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                if(callCameraIntent.resolveActivity(packageManager) != null) {
//                    val authorities = packageName + ".fileprovider"
//                    val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
//                    callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//
//                    startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
//                }
            } catch (e: IOException) {
                Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            CAMERA_REQUEST_CODE -> {

//                if (resultCode == Activity.RESULT_OK) {
//
//                    if (null == mfilePathCallback) {
//                        return
//                    }
//                    mfilePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,data))
//                    mfilePathCallback = null
//
////                    val result = if (intent == null || resultCode != RESULT_OK)
////                        null
////                    else
////                        intent.getData()
////                    val resultsArray = arrayOfNulls<Uri>(1)
////
////                    resultsArray[0] = result
////
////                    if (mfilePathCallback != null) {
////                        mfilePathCallback.onReceiveValue(resultsArray)
////                    }
//
//                    this!!.parentLayout?.let {
//                        Snackbar.make(it, "Uploading Session Photo", Snackbar.LENGTH_INDEFINITE)
//                                .setAction("HIDE") { }
//                                .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
//                                .show()
//                    }
//
//
//                }

                if (resultCode == RESULT_CANCELED) {
                    // this is important, call the callback with null parameter
                    this.mfilePathCallback!!.onReceiveValue(null);
                } else if (resultCode == RESULT_OK) {
                    // extract the uri(s) you need here
                    val resultsArray = arrayOfNulls<Uri>(1)
                    resultsArray[0] = if (intent == null || resultCode != RESULT_OK)
                        null
                    else
                        intent.getData()
                    this.mfilePathCallback!!.onReceiveValue(resultsArray);
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

@Throws(IOException::class)
fun createImageFile(): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName: String = "JPEG_" + timeStamp + "_"
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if(!storageDir.exists()) storageDir.mkdirs()
    val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
    imageFilePath = imageFile.absolutePath
    return imageFile
}
 private fun OnCameraOpenPressed() {
     if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
             != PackageManager.PERMISSION_GRANTED) {
         if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                         Manifest.permission.READ_EXTERNAL_STORAGE)) {


             //openCamera()
         } else {

             ActivityCompat.requestPermissions(this,
                     arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                     3333)

         }
     }
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {


            //openCamera()
        } else {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    5885)

        }
    } else {
        //Toast.makeText(getApplicationContext(), "camera granted", Toast.LENGTH_LONG).show();
        openCamera()
    }
    return;
}
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            5885 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openCamera()

                } else {
                    // permission denied, boo! Disable the
                    Toast.makeText(this,"boom" , Toast.LENGTH_LONG)
                    // functionality that depends on this permission.
                }
                return
            }
            3333-> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    Toast.makeText(this,"boom" , Toast.LENGTH_LONG)
                    // functionality that depends on this permission.
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun openCamera():Boolean{
        try {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imageFile = createImageFile()
            photoURI = Uri.fromFile(imageFile)
            Toast.makeText(this@MainActivity, "photoURI"+photoURI.toString(), Toast.LENGTH_SHORT).show()
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (e: IOException) {
            Toast.makeText(this@MainActivity,"FROM HERE \n Could not create file",Toast.LENGTH_LONG).show()
        }
        return true

    }

    override fun onBackPressed() {

        if (mainWeb != null) {
            if (mainWeb.canGoBack()) {
                mainWeb.goBack()
            }
        }else
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    fun showNormalAlert(): Boolean {
        val dialog = AlertDialog.Builder(this).setTitle("ATTRACK").setMessage("Created By Gıyaseddin ALFARKH    KARABÜK ÜNİVERTİSTY")
                .setPositiveButton("OK", { dialog, i ->
                    Toast.makeText(applicationContext, "KBÜ", Toast.LENGTH_LONG).show()
                })
        dialog.show()
     return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.set_about -> return showNormalAlert()
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard -> {
                // Handle the camera action
                mainWeb.loadUrl("https://www.attrack.cf/")
            }
            R.id.nav_profile -> {
                mainWeb.loadUrl("https://www.attrack.cf/myProfile")
            }
            R.id.nav_program -> {
                mainWeb.loadUrl("https://www.attrack.cf/course_sessions/view")
            }
            R.id.nav_lessons -> {
                mainWeb.loadUrl("https://www.attrack.cf/lessons/view")
            }
            R.id.nav_view_studetns -> {
                mainWeb.loadUrl("https://www.attrack.cf/clever/lastAttRecords")
            }
            R.id.nav_search_studetns -> {
                mainWeb.loadUrl("https://www.attrack.cf/students/view")
            }
            R.id.nav_logout -> {
                mainWeb.loadUrl("https://www.attrack.cf/logout")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}




