package com.example.ndt_is_exciting_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.directory_layout.MainActivity
import com.example.ndt_is_exciting_app.question.DragBoxQuestion
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class SplashScreen : ComponentActivity() {

    private lateinit var sf : SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("SplashScreenActivity","SplashScreen onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(2000){

            //calls this function after delay
            chooseActivity()

            exitProcess(1)
        }

//      TODO: Create Delay
    }

    private fun chooseActivity() {
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()


        Log.i("dbug SplashScreen","in Choose Activity")
//        if(sf.getBoolean("isFirstTime",true)){
        startActivity(Intent(this@SplashScreen, DragBoxQuestion::class.java))
        editor.apply{
            putBoolean("isFirstTime",false)
            commit()
        }
//        }else {
//            startActivity(Intent(this@SplashScreen, DragBoxQuestion::class.java))
//        }
    }
}

