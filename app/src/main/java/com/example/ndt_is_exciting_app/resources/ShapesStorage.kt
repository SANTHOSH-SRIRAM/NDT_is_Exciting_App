package com.example.ndt_is_exciting_app.resources

import android.util.Log
import com.example.ndt_is_exciting_app.open_gl_interface.HollowRect


var screenWidth = 0
var screenHeight = 0
var translate = floatArrayOf(0.0f,0.0f)
var zoom = 1.0f
var availableTypes = mutableListOf("HollowRect","Point")

var shapesHollowRect : MutableList<HollowRect> = mutableListOf(HollowRect(floatArrayOf(
    0.8f,0.8f,0f,0.8f,-0.8f,0f,-0.8f,-0.8f,0f,-0.8f,0.8f,0f)))
var shapesPoint = mutableListOf<FloatArray>()

var TAG = "dbug ShapesStorage"

fun createNewShape(type : String ,vararg points : MutableList<Float>) {


    when (type) {
        "HollowRect" -> {
            lateinit var squareCoords: FloatArray
            //To accordingly change the Points
            var relativeCoords = returnRelative(points as Array<MutableList<Float>>)

            var startPoint = relativeCoords[0]
            var endPoint = relativeCoords[1]

            squareCoords = floatArrayOf(
                startPoint[0], startPoint[1], 0.0f,
                startPoint[0], endPoint[1], 0.0f,
                endPoint[0], endPoint[1], 0.0f,
                endPoint[0], startPoint[1], 0.0f
            )


            Log.i(TAG, squareCoords.contentToString())

            shapesHollowRect.add(HollowRect(squareCoords))
            Log.i(TAG, shapesHollowRect.size.toString())
        }

        "Point" -> {
            TODO("haven't implemented the point check option")
        }
    }
}

fun drawSelectionBoxes(vPMatrix :FloatArray){
    shapesHollowRect.forEach{it.draw(vPMatrix)}
}

fun returnRelative(points: Array<MutableList<Float>>): Array<MutableList<Float>> {
    //returns Coords relative to the coordinates of the OpenGl surface View in OpenGl coordinates accounting for the anti Stretch
    val ratio = screenWidth.toFloat() / screenHeight.toFloat()
    val ratioInv = screenHeight.toFloat() / screenWidth.toFloat()

    points.forEach{
        //remapping Ranges
        it[0] = ((it[0] / screenWidth)*2)-1
        it[1] = ((it[1] / screenHeight)*2)-1
        //unskewing Location
//        it[0] = it[0]
//        it[1] = it[1] * ratioInv
//        //adding Zoom
//        it.forEach{it * zoom}
//        //adding translate
//        it[0] = it[0] + translate[0]
//        it[1] = it[1] + translate[1]
    }

    return points
}