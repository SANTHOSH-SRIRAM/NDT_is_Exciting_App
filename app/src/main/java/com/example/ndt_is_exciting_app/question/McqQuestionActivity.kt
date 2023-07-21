package com.example.ndt_is_exciting_app.question

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory

class McqQuestionActivity : ComponentActivity() {

    private lateinit var mcqRecycler: RecyclerView
    private lateinit var questionView : TextView

    companion object{
        private const val TAG = "dbug MCQ Activity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mcq_question_layout)
        Log.i(TAG,"On Create")

        //Decode the Extra's in Intent

        val _intent = intent
        val topicName = _intent.getStringExtra("Topic").toString()
        val subTopicName = _intent.getStringExtra("SubTopic").toString()
        val questionNo = _intent.getIntExtra("QuestionNo",-1)

        Log.i(TAG,"after Intent")

        //Decode the Directory Information

        var currentDirectory = directory[topicName]?.get(subTopicName)?.get(questionNo)
        var currentAnswer = currentDirectory?.get("_CorrectAnswer") as String
        var noOfQuestions = currentDirectory?.get("_no Of Options") as Int
        Log.i(TAG,"after current Directory")

        //set the basic data in the Layout

        mcqRecycler = findViewById(R.id.mcq_holder)
        questionView = findViewById(R.id.mcq_question_view)

        questionView.text = currentDirectory["_Question"] as String

        mcqRecycler.adapter = MCQRecycler(
                this,
                noOfQuestions,
                topicName,subTopicName,questionNo
        )
        mcqRecycler.setHasFixedSize(true)
        mcqRecycler.layoutManager = LinearLayoutManager(this)

        Log.i(TAG, "after attaching the adapter")
    }
}
