package com.example.ndt_is_exciting_app.open_gl_interface

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class Line {
    private val VertexBuffer: FloatBuffer
    private val VertexShaderCode =  // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +  // the matrix must be included as a modifier of gl_Position
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"
    private val FragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"
    protected var GlProgram: Int
    protected var PositionHandle = 0
    protected var ColorHandle = 0
    protected var MVPMatrixHandle = 0
    private val VertexCount = LineCoords.size / COORDS_PER_VERTEX
    private val VertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    var color = floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f)

    init {
        // initialize vertex byte buffer for shape coordinates
        val bb = ByteBuffer.allocateDirect( // (number of coordinate values * 4 bytes per float)
            LineCoords.size * 4
        )
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder())

        // create a floating point buffer from the ByteBuffer
        VertexBuffer = bb.asFloatBuffer()
        // add the coordinates to the FloatBuffer
        VertexBuffer.put(LineCoords)
        // set the buffer to read the first coordinate
        VertexBuffer.position(0)
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, VertexShaderCode)
        val fragmentShader: Int =
            loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShaderCode)
        GlProgram = GLES20.glCreateProgram() // create empty OpenGL ES Program
        GLES20.glAttachShader(GlProgram, vertexShader) // add the vertex shader to program
        GLES20.glAttachShader(GlProgram, fragmentShader) // add the fragment shader to program
        GLES20.glLinkProgram(GlProgram) // creates OpenGL ES program executables
    }

    fun SetVerts(v0: Float, v1: Float, v2: Float, v3: Float, v4: Float, v5: Float) {
        LineCoords[0] = v0
        LineCoords[1] = v1
        LineCoords[2] = v2
        LineCoords[3] = v3
        LineCoords[4] = v4
        LineCoords[5] = v5
        VertexBuffer.put(LineCoords)
        // set the buffer to read the first coordinate
        VertexBuffer.position(0)
    }

    fun SetColor(red: Float, green: Float, blue: Float, alpha: Float) {
        color[0] = red
        color[1] = green
        color[2] = blue
        color[3] = alpha
    }

    fun draw() {//mvpMatrix: FloatArray?
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(GlProgram)

        // get handle to vertex shader's vPosition member
        PositionHandle = GLES20.glGetAttribLocation(GlProgram, "vPosition")

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(PositionHandle)

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            PositionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            VertexStride,
            VertexBuffer
        )

        // get handle to fragment shader's vColor member
        ColorHandle = GLES20.glGetUniformLocation(GlProgram, "vColor")

        // Set color for drawing the triangle
        GLES20.glUniform4fv(ColorHandle, 1, color, 0)

        // get handle to shape's transformation matrix
//        MVPMatrixHandle = GLES20.glGetUniformLocation(GlProgram, "uMVPMatrix")
//        ArRenderer.checkGlError("glGetUniformLocation")
//
//        // Apply the projection and view transformation
//        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0)
//        ArRenderer.checkGlError("glUniformMatrix4fv")

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, VertexCount)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(PositionHandle)
    }

    companion object {
        // number of coordinates per vertex in this array
        const val COORDS_PER_VERTEX = 3
        var LineCoords = floatArrayOf(
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f
        )
    }
}