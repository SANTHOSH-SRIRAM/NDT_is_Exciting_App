package com.example.ndt_is_exciting_app.open_gl_interface

import android.util.Log

var squareCoords = floatArrayOf(
    -0.5f,  0.5f, 0.0f,      // top left
    -0.5f, -0.5f, 0.0f,      // bottom left
    0.5f, -0.5f, 0.0f,      // bottom right
    0.5f,  0.5f, 0.0f       // top right
)

class HollowRect (private var squareCoordinates : FloatArray = squareCoords){

    private lateinit var topLine : ConstantThicknessLines
    private lateinit var bottomLine : ConstantThicknessLines
    private lateinit var rightLine : ConstantThicknessLines
    private lateinit var leftLine : ConstantThicknessLines

    companion object {
        private const val TAG = "dbug hollowRect"
    }

    init{
        createConstantThicknessLines()
    }

    private fun createConstantThicknessLines(){
        var coords = squareCoordinates

        var topCoords = coords.sliceArray(0..5)
        var rightCoords = coords.sliceArray(3..8)
        var bottomCoords = coords.sliceArray(6..11)
        var leftCoords = coords.sliceArray(setOf(0,1,2,9,10,11))
        Log.i(TAG,"after slicing")

        topLine = ConstantThicknessLines(topCoords)
        rightLine = ConstantThicknessLines(rightCoords)
        bottomLine = ConstantThicknessLines(bottomCoords)
        leftLine = ConstantThicknessLines(leftCoords)


//        topLine.SetVerts(coords[0],coords[1],coords[2],coords[3],coords[4],coords[5])
    }

    fun SetVerts(
        x1 : Float,y1 : Float,z1 : Float,
        x2 : Float,y2 : Float,z2 : Float,
        x3 : Float,y3 : Float,z3 : Float,
        x4 : Float,y4 : Float,z4 : Float
    ){
        this.squareCoordinates = floatArrayOf(
            x1,y1,z1,
            x2,y2,z2,
            x3,y3,z3,
            x4,y4,z4
        )
        createConstantThicknessLines()
    }

    fun draw(vPMatrix : FloatArray){
        this.topLine.draw(vPMatrix)
        this.rightLine.draw(vPMatrix)
        this.bottomLine.draw(vPMatrix)
        this.leftLine.draw(vPMatrix)
    }
}