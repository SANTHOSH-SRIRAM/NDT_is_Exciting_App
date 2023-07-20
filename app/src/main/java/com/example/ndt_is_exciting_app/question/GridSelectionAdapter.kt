package com.example.ndt_is_exciting_app.question

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.directory.directory
import com.example.ndt_is_exciting_app.resources.GridSize
import java.lang.Math.min

class GridSelectionAdapter(private val context: Context,
                           private val gridNo : Int,
                           private val gridSize: GridSize,
                           private var topicName : String,
                           private var subTopicName : String,
                           private var questionNo: String
) :
    RecyclerView.Adapter<GridSelectionAdapter.ViewHolder>() {


    var currentDirectory = (directory[topicName]?.get(subTopicName))?.get(questionNo.toInt())

    companion object{
        const val Margin_Size = 10
        const val TAG = "dbug GridSelectionAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        for (i in 0 until gridNo){

        }

        val view : View = LayoutInflater.from(context).inflate(R.layout.grid_selection_box_option,parent,false)
        val cardHeight = parent.height / gridSize.getHeight() - (2 * Margin_Size)
        val cardWidth = parent.width / gridSize.getWidth()  - (2 * Margin_Size)
        val minLength = min(cardWidth,cardHeight)

        val layoutParams = view.findViewById<CardView>(R.id.gridSelectOption).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = minLength
        layoutParams.height = minLength
        layoutParams.setMargins(Margin_Size, Margin_Size, Margin_Size, Margin_Size)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = gridNo

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val gridSelectText = itemView.findViewById<TextView>(R.id.gridSelectText)
        private val gridSelectImage = itemView.findViewById<ImageView>(R.id.gridSelectImage)
        private val gridSelectOption = itemView.findViewById<CardView>(R.id.gridSelectOption)
        private val selectOutline = context.getDrawable(R.drawable.bg_roundrect_ripple_light_border)
        fun bind(position: Int) {
            var ansResource = currentDirectory?.get(position + 1)
            if( ansResource is Int ){
                var imageResource : Drawable? = context.getDrawable(ansResource)
                gridSelectImage.setImageResource(imageResource)
                gridSelectImage.visibility = View.VISIBLE
            }else if(ansResource is String){
                gridSelectText.text = ansResource;
                gridSelectText.visibility = View.VISIBLE
            }

            gridSelectOption.setOnClickListener{
                Log.i(TAG, "Clicked on position $position")
                if (gridSelectOption.foreground == selectOutline){
                    gridSelectOption.foreground = null
                }else {
                    gridSelectOption.foreground = selectOutline
                }
            }
        }
    }
}