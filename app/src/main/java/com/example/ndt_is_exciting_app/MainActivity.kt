package com.example.ndt_is_exciting_app

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.ui.theme.NDT_is_Exciting_AppTheme
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : ComponentActivity() {

    private lateinit var mcqRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_layout)


        mcqRecycler = findViewById<RecyclerView>(R.id.mcq_holder)
        mcqRecycler.adapter = MCQRecycler(this,4)
        mcqRecycler.setHasFixedSize(true)
        mcqRecycler.layoutManager = LinearLayoutManager(this)
        R.
        //R.layout.mcq_options_card
        //R.layout.opening
    }
}

