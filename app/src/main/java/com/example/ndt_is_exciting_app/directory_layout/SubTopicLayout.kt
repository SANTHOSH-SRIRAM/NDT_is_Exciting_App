package com.example.ndt_is_exciting_app.directory_layout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory

class SubTopicLayout : ComponentActivity() {


    private lateinit var questionRecycler: RecyclerView
    private var noOfQuestions = 0

    companion object {
        const val TAG = "dbug SubTopicLayout"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_topic_layout)
        Log.i(TAG,"in OnCreate")

        var _intent = getIntent()
        var topicName = _intent.getStringExtra("Topic")
        var subTopicName = _intent.getStringExtra("SubTopic")

        noOfQuestions = directory[topicName]?.get(subTopicName)?.size ?:
                Log.i(TAG,"after receiving Intent")

        Log.i(TAG,"after assigning the Topic List")

        questionRecycler = findViewById(R.id.questions)
        //mainTextView = findViewById(R.id.)

        questionRecycler.adapter = QuestionRecycler(this,noOfQuestions,subTopicName,topicName)
        questionRecycler.setHasFixedSize(true)
        questionRecycler.layoutManager = LinearLayoutManager(this)

    }
}
