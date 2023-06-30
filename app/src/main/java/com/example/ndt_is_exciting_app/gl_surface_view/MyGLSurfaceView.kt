package com.example.ndt_is_exciting_app.gl_surface_view

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.ndt_is_exciting_app.gl_surface_view.MyGLRenderer

class MyGLSurfaceView(context: Context , private var coords : MutableList<Any>) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}

