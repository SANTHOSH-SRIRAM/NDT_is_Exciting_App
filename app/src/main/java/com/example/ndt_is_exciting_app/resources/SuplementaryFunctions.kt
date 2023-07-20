package com.example.ndt_is_exciting_app.resources

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.ndt_is_exciting_app.directory.directory
import com.example.ndt_is_exciting_app.question.DragBoxQuestion
import com.example.ndt_is_exciting_app.question.GridSelectionQuestionActivity
import com.example.ndt_is_exciting_app.question.McqQuestionActivity
import kotlin.math.pow
import kotlin.math.sqrt

fun distance(pos1 : MutableList<Float>,pos2 : MutableList<Float>): Float {
    return sqrt((pos1[0]-pos2[0]).pow(2) + (pos1[1] - pos2[1]).pow(2))
}

fun setQuestion(context : Context, topicName : String, subTopicName : String, questionNo :Int) : Boolean{

    var currentDirectory = directory[topicName]?.get(subTopicName)?.get(questionNo)
    var questionType = currentDirectory?.get("_QuestionType")

    var classQuestion : Class<*>
    var type = ""

    when (questionType) {
        "MCQ" -> {
            classQuestion = McqQuestionActivity::class.java
        }

        "GridSelection" -> {
            classQuestion = GridSelectionQuestionActivity::class.java
        }

        "DragBoxSelect" -> {
            classQuestion = DragBoxQuestion::class.java
            type = "DragBoxSelect"
        }

        "PointSelect" ->{
            classQuestion = DragBoxQuestion::class.java
            type = "PointSelect"
        }

        else -> {
            return false
        }
    }

    var Intent = Intent(context, classQuestion)
        .putExtra("SubTopic",subTopicName)
        .putExtra("Topic",topicName)
        .putExtra("QuestionNo", questionNo)

    if (type != ""){
        Intent.putExtra("Type",type)
    }

    context.startActivity(Intent)
    return true
}