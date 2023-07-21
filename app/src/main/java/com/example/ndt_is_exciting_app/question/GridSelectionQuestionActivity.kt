package com.example.ndt_is_exciting_app.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.resources.GridSize

class GridSelectionQuestionActivity : ComponentActivity() {


    private lateinit var gridSelectionRecycler: RecyclerView
    private var gridSize : GridSize = GridSize.HARD
    private var Size : Int = gridSize.gridSize


    var answerLog = IntArray(Size)
    var correctAnswer = IntArray(Size)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_selection_question)

        Log.i("dbug GridSelectionQuestion","in onCreate")

        var _intent = intent
        var topicName = _intent.getStringExtra("Topic").toString()
        var subTopicName = _intent.getStringExtra("SubTopic").toString()
        var questionNo = _intent.getIntExtra("QuestionNo",-1)

        gridSelectionRecycler = findViewById(R.id.grid_selection_recycler)
        gridSelectionRecycler.adapter = GridSelectionAdapter(this,Size,gridSize,topicName,subTopicName,questionNo)
        gridSelectionRecycler.setHasFixedSize(true)
        gridSelectionRecycler.layoutManager = GridLayoutManager(this,gridSize.getWidth())
    }
}