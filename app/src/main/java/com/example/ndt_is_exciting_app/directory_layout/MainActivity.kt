package com.example.ndt_is_exciting_app.directory_layout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory

class MainActivity : ComponentActivity() {

    companion object{
        private const val TAG = "dbug MainActivity"
    }

    private lateinit var topicRecycler: RecyclerView
    private val noOfTopics = directory.size

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"in OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"after content set")
        val topics = mutableListOf<String>()
        directory.forEach{ entry -> topics.add(entry.key)}
        Log.i(TAG,"after assigning the Topic List")

        topicRecycler = findViewById(R.id.sub_topics)
        topicRecycler.adapter = TopicRecycler(this,noOfTopics,topics)
        topicRecycler.setHasFixedSize(true)
        topicRecycler.layoutManager = LinearLayoutManager(this)
    }
}

