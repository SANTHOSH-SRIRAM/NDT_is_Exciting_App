package com.example.ndt_is_exciting_app.open_gl_interface

import android.util.Log



class HollowRect (private var squareCoords : FloatArray = floatArrayOf()){

    private lateinit var topLine : ConstantThicknessLines
    private lateinit var bottomLine : ConstantThicknessLines
    private lateinit var rightLine : ConstantThicknessLines
    private lateinit var leftLine : ConstantThicknessLines

    var objectVaribles = listOf(topLine,leftLine,bottomLine,rightLine)
    companion object {
        private const val TAG = "dbug hollowRect"
    }

    init{
        if (squareCoords.size == 0) {
            squareCoords = floatArrayOf(
                -0.5f, 0.5f, 0.0f,      // top left
                -0.5f, -0.5f, 0.0f,      // bottom left
                0.5f, -0.5f, 0.0f,      // bottom right
                0.5f, 0.5f, 0.0f       // top right
            )
        }
        createConstantThicknessLines()
    }

    private fun createConstantThicknessLines(){
        var coords = squareCoords

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
        this.squareCoords = floatArrayOf(
            x1,y1,z1,
            x2,y2,z2,
            x3,y3,z3,
            x4,y4,z4
        )
        createConstantThicknessLines()
    }

    fun draw(vPMatrix : FloatArray){
        objectVaribles.forEach{it.draw(vPMatrix)}
    }

    fun getCorners(): MutableList<MutableList<Float>> {
        var corner1 = mutableListOf( squareCoords[0],squareCoords[1] )
        var corner2 = mutableListOf( squareCoords[6],squareCoords[7] )
        return mutableListOf(corner1,corner2)
    }

    fun setColor(color: FloatArray) {
        objectVaribles.forEach { it.setColor(color) }
    }
}