package com.example.ndt_is_exciting_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.resourses.isFirstTime

class MainActivity : ComponentActivity() {

//    private lateinit var mcqRecycler: RecyclerView
//    private val mcqSize = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mcqRecycler = findViewById(R.id.mcq_holder)
//        mcqRecycler.adapter = MCQRecycler(this,mcqSize)
//        mcqRecycler.setHasFixedSize(true)
//        mcqRecycler.layoutManager = LinearLayoutManager(this)
    }
}

