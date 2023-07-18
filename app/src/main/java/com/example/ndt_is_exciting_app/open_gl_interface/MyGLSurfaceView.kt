package com.example.ndt_is_exciting_app.open_gl_interface

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.core.view.GestureDetectorCompat
import com.example.ndt_is_exciting_app.openGLPackageTag
import com.example.ndt_is_exciting_app.resources.createNewShape
import com.example.ndt_is_exciting_app.resources.reset_all
import com.example.ndt_is_exciting_app.resources.translate
import com.example.ndt_is_exciting_app.resources.zoom
import kotlin.math.max
import kotlin.math.min


private var mScaleFactor = 1.0f

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mScaleGestureDetector: ScaleGestureDetector


    private var previousX: Float = 0f
    private var previousY: Float = 0f
    private lateinit var startPos: MutableList<Float>
    private lateinit var endPos: MutableList<Float>

    private var longPressActive = false
    private var options = listOf("PointSelect","DragBoxSelect" )
    private var questionType = "PointSelect"


    companion object {
        private const val ROTATE_FACTOR: Float = 180.0f / 320f
        private const val TRANSLATE_FACTOR: Float = 0.002f
        private const val TAG = "dbug Surface View"
    }

    init {
        Log.i(openGLPackageTag, "init of My Surface View")

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)
        reset_all()
        mDetector = GestureDetectorCompat(context, MyGestureListener(this))
        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener(this))



        Log.i(openGLPackageTag, "init of My Surface View")
        renderer = MyGLRenderer(this.context)
        Log.i(openGLPackageTag, "init of My Surface View")
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        val x: Float = e.x
        val y: Float = e.y

        mDetector.onTouchEvent(e)
        var scale = mScaleGestureDetector.onTouchEvent(e)



        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                if (longPressActive){
//                    endPos = Mutable
                    Log.i(TAG,"Long Press Move")
                }else {

                    var dx: Float = x - previousX
                    var dy: Float = y - previousY

                    translate[0] -= dx * TRANSLATE_FACTOR
                    translate[1] += dy * TRANSLATE_FACTOR

                    requestRender()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (longPressActive){
                    endPos = mutableListOf(e.x,e.y)
                    if (questionType == "DragBoxSelect"){
                        createNewShape("HollowRect",startPos,endPos)
                    }else if(questionType =="PointSelect"){
                        createNewShape("Point",endPos)
                    }
                    requestRender()
                    longPressActive = false
                }
            }

        }
        previousX = x
        previousY = y
        return true
    }

    private class MyGestureListener(private val View : MyGLSurfaceView) : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(event: MotionEvent): Boolean {
            Log.i(TAG, "onDown: $event")
            return true
        }

        override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.i(TAG, "onFling: $event1 $event2")
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            View.startPos = mutableListOf(e.x,e.y)
            View.longPressActive = true
        }
    }

    private class ScaleListener(private val View : MyGLSurfaceView) : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
            Log.i(TAG, "onScale,$mScaleFactor")
            zoom = mScaleFactor
            View.requestRender()
            return true
        }
    }
}

