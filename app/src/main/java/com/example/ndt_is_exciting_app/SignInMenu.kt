package com.example.ndt_is_exciting_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.directory_layout.MainActivity

class SignInMenu : ComponentActivity() {
    
    private lateinit var signInButton : Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_menu)

        signInButton = findViewById(R.id.signInButton)
        
        signInButton.setOnClickListener{
            startActivity(Intent(this@SignInMenu, MainActivity::class.java))
        }
    }

    fun signInAction(){

    }
}