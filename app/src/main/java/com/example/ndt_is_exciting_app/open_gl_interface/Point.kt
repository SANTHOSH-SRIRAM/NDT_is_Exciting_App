package com.example.ndt_is_exciting_app.open_gl_interface

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin


//class Point (private var pointCoords :FloatArray = floatArrayOf()) {
//    private lateinit var vertexBuffer: FloatBuffer
//    private lateinit var drawListBuffer: ShortBuffer
//
//    private val coordsPerVertex = 3
//    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
//    var color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
//    var size = 0.05f
//
//    // Use to access and set the view transformation
//    private var vPMatrixHandle: Int = 0
//
//    private val vertexShaderCode =
//    // This matrix member variable provides a hook to manipulate
//        // the coordinates of the objects that use this vertex shader
//        "uniform mat4 uMVPMatrix;" +
//                "attribute vec4 vPosition;" +
//                "void main() {" +
//                // the matrix must be included as a modifier of gl_Position
//                // Note that the uMVPMatrix factor *must be first* in order
//                // for the matrix multiplication product to be correct.
//                "  gl_Position = uMVPMatrix * vPosition;" +
//                "}"
//
//
//    private val fragmentShaderCode =
//        "precision mediump float;" +
//                "uniform vec4 vColor;" +
//                "void main() {" +
//                "  gl_FragColor = vColor;" +
//                "}"
//
//    // initialize vertex byte buffer for shape coordinates
//
//    private var mProgram: Int
//    private var positionHandle: Int = 0
//    private var mColorHandle: Int = 0
//
//    private val vertexCount: Int = pointCoords.size / coordsPerVertex
//    private val vertexStride: Int = coordsPerVertex * 4 // 4 bytes per vertex
//
//
//    init {
//        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
//        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
//
//        if(pointCoords.isEmpty()) {
//            pointCoords = floatArrayOf(
//                0.0f, 0.0f, 0.0f
//            )
//        }
//
//        evaluateVertexBuffer()
//
//        // create empty OpenGL ES Program
//        mProgram = GLES20.glCreateProgram().also {
//
//            // add the vertex shader to program
//            GLES20.glAttachShader(it, vertexShader)
//
//            // add the fragment shader to program
//            GLES20.glAttachShader(it, fragmentShader)
//
//            // creates OpenGL ES program executables
//            GLES20.glLinkProgram(it)
//
//        }
//    }
//
//    fun setPointCoords(pointCoords: FloatArray){
//        this.pointCoords = pointCoords
//        evaluateVertexBuffer()
//    }
//
//    fun SetColor(Red : Float,Blue : Float,Green : Float,Alpha : Float){
//        color = floatArrayOf(Red,Blue,Green,Alpha)
//    }
//
//    private fun evaluateVertexBuffer(){
//
//        var squareCoords = floatArrayOf(
//            pointCoords[0] - size,pointCoords[1] + size,0.0f,
//            pointCoords[0] + size,pointCoords[1] + size,0.0f,
//            pointCoords[0] + size,pointCoords[1] - size,0.0f,
//            pointCoords[0] - size,pointCoords[1] - size,0.0f,
//        )
//
//        vertexBuffer =
//                // (# of coordinate values * 4 bytes per float)
//            ByteBuffer.allocateDirect(squareCoords.size * 4).run {
//                order(ByteOrder.nativeOrder())
//                asFloatBuffer().apply {
//                    put(squareCoords)
//                    position(0)
//                }
//            }
//
//        // initialize byte buffer for the draw list
//        drawListBuffer =
//                // (# of coordinate values * 2 bytes per short)
//            ByteBuffer.allocateDirect(drawOrder.size * 2).run {
//                order(ByteOrder.nativeOrder())
//                asShortBuffer().apply {
//                    put(drawOrder)
//                    position(0)
//                }
//            }
//    }
//
//
//
//    open fun draw(mvpMatrix : FloatArray) {
//        // Add program to OpenGL ES environment
//        GLES20.glUseProgram(mProgram)
//
//        // get handle to vertex shader's vPosition member
//        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
//
//            // Enable a handle to the triangle vertices
//            GLES20.glEnableVertexAttribArray(it)
//
//            // Prepare the triangle coordinate data
//            GLES20.glVertexAttribPointer(
//                it,
//                coordsPerVertex,
//                GLES20.GL_FLOAT,
//                false,
//                vertexStride,
//                vertexBuffer
//            )
//
//            // get handle to fragment shader's vColor member
//            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
//
//                // Set color for drawing the triangle
//                GLES20.glUniform4fv(colorHandle, 1, color, 0)
//            }
//
//            // get handle to shape's transformation matrix
//            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
//
//            // Pass the projection and view transformation to the shader
//            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
//
//
//            // Draw the two triangle
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)
//
//            // Disable vertex array
//            GLES20.glDisableVertexAttribArray(it)
//        }
//    }
//
//    fun getPoint(): MutableList<Float> {
//        return mutableListOf(pointCoords[0],pointCoords[1])
//    }
//}

class Point( private var pointCoords : FloatArray = floatArrayOf() ,thicknessLines :Float= 0.005f) {

    companion object {
        private const val TAG = "dbug Points"
    }

    private var size = 0.05f

    private lateinit var squareCoords: FloatArray
    private lateinit var square : Squares

    init {
        if (pointCoords.isEmpty()){
            pointCoords = floatArrayOf(0.0f,0.0f,0.0f)
        }
        createPoint()
    }

    private fun createPoint(){

        var squareCoords = floatArrayOf(
            pointCoords[0] - size,pointCoords[1] + size,0.0f,
            pointCoords[0] + size,pointCoords[1] + size,0.0f,
            pointCoords[0] + size,pointCoords[1] - size,0.0f,
            pointCoords[0] - size,pointCoords[1] - size,0.0f,
        )
        Log.i(TAG,"init of Square Coords ${squareCoords.contentToString()}")


        square = Squares(squareCoords)
        Log.i(TAG,"evaluated Vertex Coords")
    }

    fun setPoint(x1:Float, y1:Float, z1:Float,){
        this.pointCoords = floatArrayOf(x1,y1,z1)
        createPoint()
    }

    fun getPoint(): MutableList<Float> {
        return mutableListOf(pointCoords[0],pointCoords[1],pointCoords[2])
    }

    fun setPoint(array : FloatArray){
        var x1 = array[0]
        var y1 = array[1]
        var z1 = array[2]
        this.setPoint(x1,y1,z1)
    }

    fun setColor(array :FloatArray){
        square.color = array
    }

    fun draw(vPMatrix:FloatArray){
        square.draw(vPMatrix)
    }
}

