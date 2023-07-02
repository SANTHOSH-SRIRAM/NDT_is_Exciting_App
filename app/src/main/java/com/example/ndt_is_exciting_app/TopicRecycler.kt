package com.example.ndt_is_exciting_app

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TopicRecycler(private val context: Context ,private val noOfTopics : Int) :
    RecyclerView.Adapter<TopicRecycler.ViewHolder>() {

    companion object{
        const val Margin_Size = 20
        const val TAG = "TopicRecycler"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(context).inflate(R.layout.mcq_card_image,parent,false)
        val cardHeight = parent.height / noOfTopics - (2 * Margin_Size)
        val cardWidth = parent.width - (2 * Margin_Size)


        val layoutParams = view.findViewById<CardView>(R.id.multiple_choice_bar).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardWidth
        layoutParams.height = cardHeight
        layoutParams.setMargins(Margin_Size, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = noOfTopics

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val mcqOption = itemView.findViewById<ImageView>(R.id.imageView3)

        fun bind(position: Int) {

            //mcqOption.setImageResource()
            mcqOption.setOnClickListener{
                Log.i( TAG , "Clicked on position $position")

            }
        }
    }
}