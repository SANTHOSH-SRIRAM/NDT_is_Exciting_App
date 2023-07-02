package com.example.ndt_is_exciting_app.open_gl_interface

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.example.ndt_is_exciting_app.openGLPackageTag
import java.nio.FloatBuffer

class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var mTriangle: Triangles
    private lateinit var mSquare: Squares
    private lateinit var spaceCoordsTri : FloatArray
    private lateinit var spaceCoordsQuad : FloatArray

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        Log.i(openGLPackageTag,"onSurfaceCreated")
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

//        spaceCoordsTri = floatArrayOf(     // in counterclockwise order:
//            0.0f, 0.622008459f, 0.0f,      // top
//            -0.5f, -0.311004243f, 0.0f,    // bottom left
//            0.5f, -0.311004243f, 0.0f      // bottom right
//        )
//
//        spaceCoordsQuad = floatArrayOf(
//            -0.5f,  0.5f, 0.0f,      // top left
//            -0.5f, -0.5f, 0.0f,      // bottom left
//            0.5f, -0.5f, 0.0f,      // bottom right
//            0.5f,  0.5f, 0.0f       // top right
//        )

        mSquare = Squares()
        mTriangle = Triangles()
    }

    override fun onDrawFrame(unused: GL10) {
        Log.i(openGLPackageTag,"onDrawFrame")
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        mSquare.draw()
        mTriangle.draw()
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
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