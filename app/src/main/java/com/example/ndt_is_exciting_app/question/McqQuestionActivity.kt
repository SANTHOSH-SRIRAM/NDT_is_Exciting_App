package com.example.ndt_is_exciting_app.question

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R

class McqQuestionActivity : ComponentActivity() {

    private lateinit var mcqRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mcq_question_layout)

        mcqRecycler = findViewById(R.id.mcq_holder)
        mcqRecycler.adapter = MCQRecycler(this,4)
        mcqRecycler.setHasFixedSize(true)
        mcqRecycler.layoutManager = LinearLayoutManager(this)



    }
}
