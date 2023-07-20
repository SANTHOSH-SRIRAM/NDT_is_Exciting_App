package com.example.ndt_is_exciting_app.open_gl_interface

import android.util.Log
import kotlin.math.cos
import kotlin.math.atan
import kotlin.math.sin
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

var _lineCoords = floatArrayOf(
    0.0f, -1f, 0.0f,      // top
    -1f, 1f, 0.0f    // bottom left
)

class ConstantThicknessLines( private var lineCoords : FloatArray = _lineCoords ,thicknessLines :Float= 0.005f) {

    companion object {
        private const val TAG = "dbug Constant line Thickness"
    }

    private var thicknessLines = thicknessLines

    private lateinit var squareCoords: FloatArray
    private lateinit var square : Squares

    private var theta: Float = 0.0f
    private var normalTheta: Float = 0.0f

    private lateinit var direction: FloatArray
    private lateinit var scaledDirection : FloatArray
    private lateinit var normal: FloatArray
    private lateinit var scaledNormal: FloatArray

    init {
        addThickness()
    }

    private fun addThickness(){

        //var neg_change_x = if

        direction = floatArrayOf(
            lineCoords[3] - lineCoords[0],
            lineCoords[4] - lineCoords[1],
            lineCoords[5] - lineCoords[2]
        ).normalise()

        theta = atan(direction[1] / direction[0])


        normalTheta = theta + (PI/2).toFloat()
        makeNormalTheta()
        Log.i(TAG,"theta $theta ,normalTheta $normalTheta")


        normal = floatArrayOf(cos(normalTheta),sin(normalTheta),0.0f).normalise()
        Log.i(TAG,"normal ${normal.contentToString()} ,direction ${direction.contentToString()}")


        scaledNormal = normal.scaled(thicknessLines)
        scaledDirection = normal.scaled(thicknessLines)
        Log.i(TAG,"scaled , ScaledNormal${scaledNormal.contentToString()} , Normal${scaledDirection.contentToString()}")

        squareCoords = floatArrayOf(
            lineCoords[0] + scaledNormal[0]  , lineCoords[1] + scaledNormal[1] , lineCoords[2],
            lineCoords[3] + scaledNormal[0]  , lineCoords[4] + scaledNormal[1] , lineCoords[5],
            lineCoords[3] - scaledNormal[0]  , lineCoords[4] - scaledNormal[1] , lineCoords[5],
            lineCoords[0] - scaledNormal[0]  , lineCoords[1] - scaledNormal[1] , lineCoords[2]
        )
        Log.i(TAG,"init of Square Coords ${squareCoords.contentToString()}")


        square = Squares(squareCoords)
        Log.i(TAG,"evaluated Vertex Coords")
    }

    fun makeNormalTheta(){
        while (normalTheta > 0){
            normalTheta -= PI.toFloat()
        }
    }

    fun setVerts(x1:Float, y1:Float, z1:Float, x2:Float, y2:Float, z2:Float){
        this.lineCoords = floatArrayOf(x1,y1,z1,x2,y2,z2)
        addThickness()
    }

    fun setVerts(array : FloatArray){
        var x1 = array[0]
        var y1 = array[1]
        var z1 = array[2]
        var x2 = array[3]
        var y2 = array[4]
        var z2 = array[5]
        this.setVerts(x1,y1,z1,x2,y2,z2)
    }

    fun draw(vPMatrix:FloatArray){
        square.draw(vPMatrix)
    }

    fun setColor(color: FloatArray) {
        square.color = color
    }
}

private fun FloatArray.scaled(thicknessLines: Float): FloatArray {

    return floatArrayOf(this[0] * thicknessLines,this[1] * thicknessLines,this[2] * thicknessLines)
}

private fun FloatArray.normalise(): FloatArray {
    var squareSum = 0.0
    for (i in this){
        squareSum += i.toDouble().pow(2.0)
    }
    var length = sqrt(squareSum).toFloat()

    return floatArrayOf(this[0]/length,this[1]/length,this[2]/length)
}
