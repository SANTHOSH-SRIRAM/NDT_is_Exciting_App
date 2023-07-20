package com.example.ndt_is_exciting_app.directory_layout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory
import com.example.ndt_is_exciting_app.question.DragBoxQuestion
import com.example.ndt_is_exciting_app.question.GridSelectionQuestionActivity
import com.example.ndt_is_exciting_app.question.McqQuestionActivity

class QuestionRecycler(
    private val context: Context,
    private val noOfQuestions: Int,
    private val subTopicName: String?,
    private val topicName: String?
) :
    RecyclerView.Adapter<QuestionRecycler.ViewHolder>() {

    companion object{
        const val Margin_Size = 20
        const val Margin_Left = 60
        const val TAG = "dbug QuestionRecycler"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.question_header_card,parent,false)

        val cardWidth = parent.width - (2 * Margin_Size) - (Margin_Left)
        val cardHeight = Math.min((cardWidth * 0.3f).toInt(), 300)


        val layoutParams = view.findViewById<CardView>(R.id.question_holder_card).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardWidth
        layoutParams.height = cardHeight
        layoutParams.setMargins(Margin_Size + Margin_Left, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = noOfQuestions

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val questionImage = itemView.findViewById<TextView>(R.id.question_iv_holder)


        fun bind(position: Int) {
            var currentDirectory = directory[topicName]?.get(subTopicName)?.get(position+1)
            questionImage.text = currentDirectory?.get("_name").toString()
            //mcqOption.setImageResource()
            var questionType = currentDirectory?.get("_QuestionType")

            questionImage.setOnClickListener{
                Log.i( TAG , "Clicked on position $position")
                chooseQuestion(questionType as String)
            }
        }

        fun chooseQuestion(questionType : String?) : Boolean{
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
                .putExtra("QuestionNo", position +1)

            if (type != ""){
                Intent.putExtra("Type",type)
            }

            context.startActivity(Intent)
            return true
        }
    }
}
