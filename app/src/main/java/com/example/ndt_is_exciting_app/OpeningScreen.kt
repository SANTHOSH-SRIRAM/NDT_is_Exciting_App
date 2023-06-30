package com.example.ndt_is_exciting_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.question.DragBoxQuestion

class OpeningScreen : ComponentActivity() {

    private lateinit var openingImage :ImageView
    private lateinit var next_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_screen)

        openingImage = findViewById(R.id.opening_image)
        next_button = findViewById(R.id.next_button)
        openingImage.scaleX = 5F

        next_button.setOnClickListener{
            startActivity(Intent(this@OpeningScreen, DragBoxQuestion::class.java)) //change to Sign in Activity
        }


    }
}

