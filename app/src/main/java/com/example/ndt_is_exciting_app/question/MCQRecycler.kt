package com.example.ndt_is_exciting_app.question

import android.app.Activity
import android.content.Context
import android.transition.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory

class MCQRecycler(
    private val context: Context,
    private val mcqSize: Int,
    private val topicName : String,
    private val subTopicName : String,
    private val questionNo : Int
) :
RecyclerView.Adapter<MCQRecycler.ViewHolder>() {

    private lateinit var option_typeing : String
    private var currentDirectory = directory[topicName]?.get(subTopicName)?.get(questionNo)
    private val correctAnswer = currentDirectory?.get("_CorrectAnswer")

    companion object{
        const val Margin_Size = 20
        const val TAG = "dbug MCQ Recycler"

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i( TAG , "onCreate")
        val view : View = LayoutInflater.from(context).inflate(R.layout.mcq_card_header_card,parent,false)
        val cardHeight = parent.height / mcqSize - (2 * Margin_Size)
        val cardWidth = parent.width - (2 * Margin_Size)

//        option_typeing = currentDirectory["_QuestionType"].toString()

        val layoutParams = view.findViewById<CardView>(R.id.multiple_choice_bar).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardWidth
        layoutParams.height = cardHeight
        layoutParams.setMargins(Margin_Size, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i( TAG , "onBind")
        holder.bind(position)
    }

    override fun getItemCount() = mcqSize

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //private val mcqOptionImage = itemView.findViewById<ImageView>(R.id.mcq_answer_iv)
        private val mcqOptionText = itemView.findViewById<TextView>(R.id.mcq_answer_tv)

        fun bind(position: Int) {
            Log.i( TAG , "in Bind")
            val activity = this.itemView.context as Activity

            mcqOptionText.visibility = View.VISIBLE
            mcqOptionText.text = currentDirectory?.get(position+1) as String
            Log.i( TAG , "after assigning Text")
            //mcqOption.setImageResource()
            itemView.setOnClickListener{
                Log.i( TAG , "Clicked on position $position")
                val text = "Hello toast!"
                val duration = Toast.LENGTH_SHORT
                if(position+1 == correctAnswer){
                    Toast.makeText(context, text, duration).show()

                }
            }
        }
    }
}

fun ImageView.setImageResource(any: Any?) {

}
