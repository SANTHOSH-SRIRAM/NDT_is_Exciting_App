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
import java.lang.Math.min

class SubTopicRecycler(private val context: Context, private val noOfSubTopics : Int ,private val subTopics : MutableList<String>,private val topic :String?) :
    RecyclerView.Adapter<SubTopicRecycler.ViewHolder>() {

    companion object{
        const val Margin_Size = 20
        const val Margin_Left = 60
        const val TAG = "dbug SubTopicRecycler"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.sub_topics_header_card,parent,false)

        val cardWidth = parent.width - (2 * Margin_Size) - (Margin_Left)
        val cardHeight = min((cardWidth*0.3f).toInt(),300)


        val layoutParams = view.findViewById<CardView>(R.id.sub_topics_holder_card).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardWidth
        layoutParams.height = cardHeight
        layoutParams.setMargins(Margin_Size + Margin_Left, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = noOfSubTopics

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val sub_topics_image = itemView.findViewById<TextView>(R.id.sub_topics_iv_holder)


        fun bind(position: Int) {
            val subTopicName = subTopics[position]
            sub_topics_image.text = subTopicName

            sub_topics_image.setOnClickListener{
                Log.i( TAG , "Clicked on position $position")

                context.startActivity(Intent(context, SubTopicLayout::class.java)
                    .putExtra("SubTopic",subTopicName).putExtra("Topic",topic))
            }
        }
    }
}