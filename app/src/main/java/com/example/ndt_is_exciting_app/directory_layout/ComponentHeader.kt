package com.example.ndt_is_exciting_app.directory_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory
import com.example.ndt_is_exciting_app.resources.setQuestion

class ComponentHeader : ComponentActivity() {

    private lateinit var header :TextView
    private lateinit var textView1 : TextView
    private lateinit var imageView1 : ImageView
    private lateinit var textView2 : TextView
    private lateinit var nextButton : Button


    companion object {
        private var TAG = "dbug Component Header"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"created activity")
        setContentView(R.layout.activity_component_header)
        Log.i(TAG,"applied the layout")

        header = findViewById(R.id.header_text_view)
        textView1 = findViewById(R.id.section1_tv)
        imageView1 = findViewById(R.id.section1_iv)
        textView2 = findViewById(R.id.section2_tv)
        nextButton = findViewById(R.id.nextButton)

        var _intent = intent
        var topicName = _intent.getStringExtra("Topic").toString()
        var subTopicName = _intent.getStringExtra("SubTopic").toString()

        var currentDirectory = (directory[topicName]?.get(subTopicName))

        nextButton.setOnClickListener{
            setQuestion(this,topicName,subTopicName,2)
        }

        //data
        var data_object = currentDirectory?.get(0)
        


        Log.i(TAG,"after the nameing section")
        header.text = "Radiography - X Ray"
        textView1.text = "This is the content containing the information which explained " +
                "out in the slide. here we give a brief info of the slide"
        textView2.text = "here we give some more info about the topic explaining about it , this is just some placeholder information "
//        TODO("need to implement the code for arranging the sections and retrieving the data from topics content")
    }
}