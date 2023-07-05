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

class TopicRecycler(private val context: Context ,private val noOfTopics : Int, private val topic : MutableList<String>) :
    RecyclerView.Adapter<TopicRecycler.ViewHolder>() {

    companion object{
        const val Margin_Size = 20
        const val Margin_Left = 60
        const val TAG = "dbug TopicRecycler"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i( TAG , "in OnCreate")
        val view : View = LayoutInflater.from(context).inflate(R.layout.topics_header_card,parent,false)

        val cardWidth = parent.width - (2 * Margin_Size) - (Margin_Left)
        val cardHeight = min((cardWidth*0.3f).toInt(),300)


        val layoutParams = view.findViewById<CardView>(R.id.topics_holder_card).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardWidth
        layoutParams.height = cardHeight
        layoutParams.setMargins(Margin_Size + Margin_Left, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG,"on Bind")
        holder.bind(position)
    }

    override fun getItemCount() = noOfTopics

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val topics_image = itemView.findViewById<TextView>(R.id.topics_iv_holder)


        fun bind(position: Int) {
            topics_image.text = topic[position]
            //mcqOption.setImageResource()
            topics_image.setOnClickListener{
                val activity = this.itemView.context as Activity
                Log.i( TAG , "Clicked on position $position")

                activity.startActivity(Intent(context, TopicLayout::class.java).putExtra("Topic",topic[position]))
            }
        }
    }
}