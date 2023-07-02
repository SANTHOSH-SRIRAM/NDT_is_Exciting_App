package com.example.ndt_is_exciting_app.open_gl_interface

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.example.ndt_is_exciting_app.openGLPackageTag

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init{
        Log.i(openGLPackageTag,"init of My Surface View")

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        Log.i(openGLPackageTag,"init of My Surface View")
        renderer = MyGLRenderer()
        Log.i(openGLPackageTag,"init of My Surface View")
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        // Render the view only when there is a change in the drawing data
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}

