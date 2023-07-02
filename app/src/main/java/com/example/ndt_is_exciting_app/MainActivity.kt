package com.example.ndt_is_exciting_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.directory.directory

class MainActivity : ComponentActivity() {

    private lateinit var topicRecycler: RecyclerView
    private val noOfTopics = directory.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicRecycler = findViewById(R.id.TopicRecyclerView)
        topicRecycler.adapter = TopicRecycler(this,noOfTopics)
        topicRecycler.setHasFixedSize(true)
        topicRecycler.layoutManager = LinearLayoutManager(this)
    }
}

