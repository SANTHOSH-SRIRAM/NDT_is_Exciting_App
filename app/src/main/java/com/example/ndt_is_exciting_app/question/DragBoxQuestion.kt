package com.example.ndt_is_exciting_app.question

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.gl_surface_view.MyGLSurfaceView
import kotlin.math.abs


class DragBoxQuestion : ComponentActivity() {

    private lateinit var imageQuestion : ImageView
    private lateinit var frameLayout : FrameLayout
    private lateinit var glSurface : MyGLSurfaceView

    private lateinit var startPos : MutableList<Int>
    private lateinit var endPos : MutableList<Int>
    private lateinit var layoutParams : ViewGroup.MarginLayoutParams

    private var createBox = false
    private var minBoxDimen = 10
    //private var selectionBoxes = mutableListOf<ImageView>()
    private var selectionBoxes = mutableListOf<MutableList<MutableList<Int>>>()

    companion object{
        private val Position = "Position"
        private val selectionBox = "Selection Box"
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_box_question)

        frameLayout = findViewById(R.id.dragBoxHolder)
        imageQuestion = findViewById(R.id.dragBoxImage)
        glSurface = MyGLSurfaceView(this)
        frameLayout.addView(glSurface)


        imageQuestion.setOnTouchListener{
                _, event ->
            val x = event.x.toInt()
            val y = event.y.toInt()
            Log.i(Position, "$x,$y")
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPos = mutableListOf(x,y)
                }
                MotionEvent.ACTION_MOVE -> {
                    endPos = mutableListOf(x, y)

                    selectionBoxes.add(mutableListOf(startPos,endPos))

//                    val imageView1 = selectionBoxes.last()
//                    layoutParams = imageView1.layoutParams as ViewGroup.MarginLayoutParams
//                    layoutParams.height = getHeight()
//                    layoutParams.width = getWidth()
//
//                    imageView1.maxHeight = getHeight()
//                    imageView1.maxWidth = getWidth()

//                    layoutParams.setMargins(startPos[0], startPos[1], 0, 0)
                }
                MotionEvent.ACTION_UP -> {
                    if (isMinBoxDimensions(startPos,endPos)){
                        //delete Box
                        TODO()
                        //selectionBoxes[-1]. remove from layout
                        selectionBoxes.removeLast()
                    }
//
//                    //clean up
//                    startPos = emptyList()
//                    endPos = emptyList()
//
                  }
            }
                    //add selection box to the layout
            Log.i(selectionBox,"Selection Box Created : " + selectionBoxes.size.toString())



            true
        }

    }


    private fun isMinBoxDimensions(startPos: MutableList<Int>, endPos: MutableList<Int>) :Boolean {
        val distanceX = abs(startPos[0] - endPos[0])
        val distanceY = abs(startPos[1] - endPos[1])

        return ((distanceX < minBoxDimen) or (distanceY < minBoxDimen))
    }

    private fun getHeight():Int{
        return abs(startPos[1] - endPos[1])
    }

    private fun getWidth():Int{
        return abs(startPos[1] - endPos[1])
    }



}
//    fun onClickDragBox(): View.OnDragListener? {
//        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN){
//                var x = event.getX().toInt()
//                var y = event.getY().toInt();
//            }
//            return true;
//        }
//    }

//imageQuestion.setOnTouchListener{
//    _, event ->
//    val x = event.x.toInt()
//    val y = event.y.toInt()
//    Log.i(DragBoxQuestion.Position, "$x,$y")
//    when (event.action) {
//        MotionEvent.ACTION_DOWN -> {
//            startPos = mutableListOf(x, y)
//
//            selectionBoxes.add(ImageView(this))
//            var imageView1 = selectionBoxes.last()
//
//            imageView1.setImageResource(R.drawable.drag_box)
//            frameLayout.addView(imageView1)
//
//            layoutParams = imageView1.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.height = 0
//            layoutParams.width = 0
//            layoutParams.setMargins(startPos[0], startPos[1], 0, 0)
//
//        }
//
//        MotionEvent.ACTION_MOVE -> {
//            endPos = mutableListOf(x, y)
//
//            val imageView1 = selectionBoxes.last()
//            layoutParams = imageView1.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.height = getHeight()
//            layoutParams.width = getWidth()
//
//            imageView1.maxHeight = getHeight()
//            imageView1.maxWidth = getWidth()
//
//            layoutParams.setMargins(startPos[0], startPos[1], 0, 0)
//        }
////                MotionEvent.ACTION_UP -> {
////                    if (isMinBoxDimensions(startPos,endPos)){
////                        //delete Box
////                        TODO()
////                        //selectionBoxes[-1]. remove from layout
////                        selectionBoxes.removeLast()
////                    }
////
////                    //clean up
////                    startPos = emptyList()
////                    endPos = emptyList()
////
////                  }
//    }
//    //add selection box to the layout
//    Log.i(DragBoxQuestion.selectionBox,"Selection Box Created : " + selectionBoxes.size.toString())
//
//
//
//    true
//}
//
//}

