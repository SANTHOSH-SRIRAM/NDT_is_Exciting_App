package com.example.ndt_is_exciting_app.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.resources.GridSize
import java.lang.Math.min

class GridSelectionAdapter(private val context: Context, private val gridNo : Int, private val gridSize: GridSize) :
    RecyclerView.Adapter<GridSelectionAdapter.ViewHolder>() {

    companion object{
        const val Margin_Size = 10
        const val TAG = "dbug GridSelectionAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

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
        private val gridSelectOption = itemView.findViewById<ImageView>(R.id.gridSelectImage)

        fun bind(position: Int) {

            //gridSelectOption.setImageResource(R.drawable.ic_launcher_background)
//            gridSelectOption.setOnClickListener{
//                Log.i( TAG , "Clicked on position $position")
//
//            }
        }
    }
}