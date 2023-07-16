package com.example.ndt_is_exciting_app.resources

import android.content.Context
import android.util.Log
import com.example.ndt_is_exciting_app.open_gl_interface.HollowRect
import com.example.ndt_is_exciting_app.open_gl_interface.MyGLSurfaceView
import com.example.ndt_is_exciting_app.open_gl_interface.Point


var screenWidth = 0
var screenHeight = 0
var translate = floatArrayOf(0.0f,0.0f)
var zoom = 1.0f
var availableTypes = mutableListOf("HollowRect","Point")

// CurrentShape holders
var shapesHollowRect = mutableListOf<HollowRect>()
var shapesPoint = mutableListOf<Point>()

//Answer Holder
var ansHollowRect = mutableListOf<HollowRect>(HollowRect(floatArrayOf(0.8f,0.8f,0.0f,0.8f,0.6f,0.0f,0.6f,0.6f,0.0f,0.6f,0.8f,0.0f)))
var ansPoints = mutableListOf<Point>(Point(floatArrayOf(0.8f,0.8f,0.0f)))

//Shape Buffers
var shapesHollowRectInit : MutableList<FloatArray> = mutableListOf()
var shapesPointInit : MutableList<FloatArray> = mutableListOf()
var shapePointBuffer = mutableListOf<Int>()
var shapeHollowRectBuffer = mutableListOf<Int>()

//dbug TAG
var TAG = "dbug ShapesStorage"

//Creation of Shapes function
fun createNewShape(type : String ,vararg points : MutableList<Float>) {

    var relativeCoords = returnRelative(points as Array<MutableList<Float>>)

    when (type) {
        "HollowRect" -> {
//            if(checkIfMax(shapesHollowRect as MutableList<Any>,ansHollowRect as MutableList<Any>)){
//                return
//            }
            lateinit var squareCoords: FloatArray
            //To accordingly change the Points

            var startPoint = relativeCoords[0]
            var endPoint = relativeCoords[1]

            squareCoords = floatArrayOf(
                startPoint[0], startPoint[1], 0.0f,
                startPoint[0], endPoint[1], 0.0f,
                endPoint[0], endPoint[1], 0.0f,
                endPoint[0], startPoint[1], 0.0f
            )
            shapesHollowRectInit.add((squareCoords))
            Log.i(TAG, squareCoords.contentToString())
            Log.i(TAG, shapesHollowRect.size.toString())
            Log.i(TAG, shapesHollowRect.toString())
        }

        "Point" -> {
//            if(checkIfMax(shapesPoint as MutableList<Any>, ansPoints as MutableList<Any>)){
//                return
//            }

            var pos = relativeCoords[0]
            var pointCoords = floatArrayOf(pos[0],pos[1],0.0f)
            shapesPointInit.add(pointCoords)
            Log.i(TAG,"add Point to Buffer")
        }
    }
}

//OpenGL thread Functions
fun updateShapes(){
    shapesHollowRectInit.forEach{shapesHollowRect.add(HollowRect(it))}
    shapesHollowRectInit = mutableListOf()

    shapesPointInit.forEach{shapesPoint.add(Point(it))}
    shapesPointInit = mutableListOf()
}

fun drawSelectionBoxes(vPMatrix :FloatArray){
    shapesHollowRect.forEach{it.draw(vPMatrix)}
    shapesPoint.forEach{it.draw(vPMatrix)}
}

fun updateAns(type : String, answers : List<Any>){
    when(type){
        "HollowRect" -> {
            ansHollowRect = mutableListOf()
            answers.forEach{ ansHollowRect.add(it as HollowRect)}
        }
        "Point" -> {
            ansPoints = mutableListOf()
            answers.forEach{ ansPoints.add(it as Point)}
        }
    }
}

//Utility Functions

fun returnRelative(points: Array<MutableList<Float>>): Array<MutableList<Float>> {
    //returns Coords relative to the coordinates of the OpenGl surface View in OpenGl coordinates accounting for the anti Stretch
    val ratio = screenWidth.toFloat() / screenHeight.toFloat()
    val ratioInv = screenHeight.toFloat() / screenWidth.toFloat()

    points.forEach{
        //remapping Ranges
        it[0] = ((it[0] / screenWidth)*2)-1
        it[1] = ((it[1] / screenHeight)*-2)+1
        //unskewing Location
        it[0] = it[0]
        it[1] = it[1] * ratioInv

        //adding Zoom
        it.forEach{ it -> it * zoom }

        //adding translate
        it[0] = it[0] + translate[0]
        it[1] = it[1] + translate[1]


    }

    return points
}

fun reset_all(){
    shapesHollowRect.clear()
    shapesPoint.clear()
}

private fun checkIfMax(current : MutableList<Any>, answers : MutableList<Any>): Boolean {
    return current.size > answers.size
}


//Answer Checking Functions
fun checkAnsHollowRect(): MutableList<Int> {
    var copyAns = ansHollowRect
    var no_of_ans = ansHollowRect.size
    var ansBuffer = mutableListOf<Int>()

    copyAns.forEach{
        ansBuffer.add(0)
        for (shapes in shapesHollowRect){
            var ans_corners = it.getCorners()
            var correct_ans_corners = shapes.getCorners()

            val approx = rectangeApproximation(ans_corners[0],ans_corners[1],correct_ans_corners[0],correct_ans_corners[1])

            if(approx > ansBuffer.last()){
                ansBuffer[ansBuffer.size-1] = approx
            }
        }
    }
    return ansBuffer
}

fun checkAnsPoint(): MutableList<Int>  {
    var copyAns = ansPoints
    var no_of_ans = ansPoints.size
    var ansBuffer = mutableListOf<Int>()
    shapePointBuffer = mutableListOf<Int>()
    shapesPoint.forEach{shapePointBuffer.add(0)}


    copyAns.forEach {
        ansBuffer.add(0)
        for (i in 0 until shapesPoint.size) {
            var shapes = shapesPoint[i]
            var ans_point = it.getPoint()
            var correct_ans_point = shapes.getPoint()

            val approx = CoordinateApproximation(ans_point, correct_ans_point)

            if(approx > shapePointBuffer[i]){shapePointBuffer[i] = approx}
            if (approx > ansBuffer.last()) {  ansBuffer[ansBuffer.size - 1] = approx }
        }
    }
    return ansBuffer
}

fun setShapeColors(View : MyGLSurfaceView, type :String){

    when(type){
        "Point"->{
            checkAnsPoint()
            for(i in 0 until shapePointBuffer.size){
                var ansBuffer = shapePointBuffer[i]
                var point = shapesPoint[i]
                var color: FloatArray
                if (ansBuffer == 2){
                        color = floatArrayOf(0.0f,0.0f,1.0f,1.0f)
                    }else if(ansBuffer == 1){
                        color = floatArrayOf(0.0f,1.0f,1.0f,1.0f)
                    }else{
                        color = floatArrayOf(1.0f,0.0f,0.0f,1.0f)
                    }
                point.setColor(color)
            }
        }
        "HollowRect"-> {

        }
    }
    View.requestRender()
}