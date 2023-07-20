package com.example.ndt_is_exciting_app.directory_layout

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory


class TopicLayout : ComponentActivity() {

    private lateinit var subTopicRecycler: RecyclerView
    private lateinit var mainTextView : TextView
    private var noOfSubTopics = 0

    companion object {
        const val TAG = "dbug TopicLayout"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_layout)
        Log.i(TAG,"in OnCreate")

        var _intent = intent
        var topicName = _intent.getStringExtra("Topic")
        noOfSubTopics = directory[topicName]?.size ?:
        Log.i(TAG,"after receiving Intent")

        val subTopics = mutableListOf<String>()
        directory[topicName]?.forEach{ entry -> subTopics.add(entry.key)}
        Log.i(TAG,"after assigning the Topic List")

        subTopicRecycler = findViewById(R.id.sub_topics)
        mainTextView = findViewById(R.id.topicsTextView)

        mainTextView.text = topicName

        subTopicRecycler.adapter = SubTopicRecycler(this,noOfSubTopics,subTopics,topicName)
        subTopicRecycler.setHasFixedSize(true)
        subTopicRecycler.layoutManager = LinearLayoutManager(this)

    }

//    fun onClickActivity( position : Int ){
//
//    }

}

