package com.example.ndt_is_exciting_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.directory_layout.ComponentHeader
import com.example.ndt_is_exciting_app.directory_layout.MainActivity
import com.example.ndt_is_exciting_app.question.DragBoxQuestion
import com.example.ndt_is_exciting_app.question.GridSelectionQuestionActivity
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class SplashScreen : ComponentActivity() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: Editor

    companion object{
        private val TAG = "dbug SplashScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"SplashScreen onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
//        chooseActivity()
//        Timer().schedule(2000){
//
//            //calls this function after delay
//            chooseActivity()
//
//            exitProcess(1)
//        }

//      TODO: Create Delay
    }

    private fun chooseActivity() {
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()


        Log.i(TAG,"in Choose Activity")
//        if(sf.getBoolean("isFirstTime",true)){
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        editor.apply{
            putBoolean("isFirstTime",false)
            commit()
        }
        Log.i(TAG,"completed choose activity")
//        }else {
//            startActivity(Intent(this@SplashScreen, DragBoxQuestion::class.java))
//        }
    }
}

