package com.example.ndt_is_exciting_app.open_gl_interface

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.example.ndt_is_exciting_app.openGLPackageTag
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)


    private lateinit var mTriangle: Triangles
    private lateinit var mSquare: Squares
    private lateinit var vertLine : Lines
    private lateinit var constLine :ConstantThicknessLines
    private lateinit var hollowRect : HollowRect

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        // Set the background frame color
        Log.i(openGLPackageTag,"onSurfaceCreated")


        vertLine = Lines()
        constLine = ConstantThicknessLines(floatArrayOf(0.00f, 0.5f, 0f, 0.0f, 0.0f, 0f))
        constLine.setVerts(0.5f, -0.5f, 0f, -0.5f, 0.0f, 0f)
        //constLine.SetColor(0.0f, 1.0f, 1.0f, 1.0f)

        hollowRect = HollowRect()
        //mLine = Line()
        mSquare = Squares()
        mSquare.setSquareCoords(floatArrayOf(-0.5f,  0.5f, 0.0f,      // top left
            -0.5f, -0.5f, 0.0f,      // bottom left
            0.8f, -0.5f, 0.0f,      // bottom right
            1.0f,  0.5f, 0.0f //0.0f, 0.5f, 0f, 0.0f, 0.0f, 0f,
        ))                 // 0.5f,0.8f,0f,0.8f,0.3f,0f,))
        mTriangle = Triangles()
    }

    override fun onDrawFrame(unused: GL10) {
        Log.i(openGLPackageTag,"onDrawFrame")
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Draw shape
//        mTriangle.draw(vPMatrix)


        //mLine.draw()
//        vertLine.draw()
        constLine.draw(vPMatrix)
        hollowRect.draw(vPMatrix)
        //mSquare.draw(vPMatrix)
//        mTriangle.draw(vPMatrix)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

        Log.i(openGLPackageTag,"onSurfaceChanged")
    }


}

fun loadShader(type: Int, shaderCode: String): Int {

    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
    return GLES20.glCreateShader(type).also { shader ->

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
    }
}