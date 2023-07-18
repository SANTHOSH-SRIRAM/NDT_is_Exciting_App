package com.example.ndt_is_exciting_app.open_gl_interface

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log
import com.example.ndt_is_exciting_app.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer


class Textures(context: Context) {

    var squareCoords = floatArrayOf(
        -1f,  1f, 0.0f,      // top left
        -1f, -1f, 0.0f,      // bottom left
        1f, -1f, 0.0f,      // bottom right
        1f,  1f, 0.0f       // top right
    )

    var texCoords = floatArrayOf(
        0f,  1f,      // top left
        0f, 0f,      // bottom left
        1f, 0f,      // bottom right
        1f,  1f       // top right
    )

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var texVertexBuffer: FloatBuffer
    private lateinit var drawListBuffer: ShortBuffer

    private val coordsPerVertex = 3
    private val texCoordsPerVertex = 2
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
    var color = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0

    private val mCubeTextureCoordinates: FloatBuffer? = null
    private var mTextureUniformHandle = 0
    private var mTextureCoordinateHandle = 0
    private val mTextureCoordinateDataSize = 2
    private var mTextureDataHandle = 0


    private val vertexShaderCode =
    // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 a_TexCoordinate;"+
                "varying vec2 v_TexCoordinate;" +
                "void main() {" +
                "  v_TexCoordinate = a_TexCoordinate;" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"


    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform sampler2D u_Texture;" +
                "varying vec2 v_TexCoordinate;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = (vColor * texture2D(u_Texture, v_TexCoordinate));" +
//                "  gl_FragColor = vColor;" +
                "}"

    // initialize vertex byte buffer for shape coordinates

    private var mProgram: Int
    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = squareCoords.size / coordsPerVertex
    private val vertexStride: Int = coordsPerVertex * 4 // 4 bytes per vertex
    private val texVertexCount: Int = texCoords.size / texCoordsPerVertex
    private val texVertexStride: Int = texCoordsPerVertex * 4

    companion object {
        private val TAG = "dbug Textures"
    }

    init {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        evaluateVertexBuffer()

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram().also {

            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)

        }

        Log.i(TAG, "after mProgram")

        mTextureDataHandle = loadTexture(context, R.drawable.first_test_image)
        Log.i(TAG, "Created Texture Handles")
    }

    private fun evaluateVertexBuffer() {

        vertexBuffer =
                // (# of coordinate values * 4 bytes per float)
            ByteBuffer.allocateDirect(squareCoords.size * 4).run {
                order(ByteOrder.nativeOrder())
                asFloatBuffer().apply {
                    put(squareCoords)
                    position(0)
                }
            }

        // initialize byte buffer for the draw list
        drawListBuffer =
                // (# of coordinate values * 2 bytes per short)
            ByteBuffer.allocateDirect(drawOrder.size * 2).run {
                order(ByteOrder.nativeOrder())
                asShortBuffer().apply {
                    put(drawOrder)
                    position(0)
                }
            }

        texVertexBuffer =
                // (# of coordinate values * 4 bytes per float)
            ByteBuffer.allocateDirect(texCoords.size * 4).run {
                order(ByteOrder.nativeOrder())
                asFloatBuffer().apply {
                    put(texCoords)
                    position(0)
                }
            }
    }


    fun draw(mvpMatrix: FloatArray) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)


        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also { it ->

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                coordsPerVertex,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate").also { it ->

                // Enable a handle to the triangle vertices
                GLES20.glEnableVertexAttribArray(it)

                // Prepare the triangle coordinate data
                GLES20.glVertexAttribPointer(
                    it,
                    texCoordsPerVertex,
                    GLES20.GL_FLOAT,
                    false,
                    texVertexStride,
                    texVertexBuffer
                )
            }

            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            // get handle to vertex shader's vPosition member
            mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture").also {

                // Set the active texture unit to texture unit 0.
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0)

                // Bind the texture to this unit.
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle)

                // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
                GLES20.glUniform1i(it, 0)
                Log.i(TAG, "set Texture ")


            }


            // get handle to shape's transformation matrix
             vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

            Log.i(TAG,"got MatrixHandle")

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

            // Draw the two triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)


            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }


        Log.i(TAG, "After Draw")
    }

    fun loadTexture(context: Context, resourceId: Int): Int {
        val textureHandle = IntArray(1)
        GLES20.glGenTextures(1, textureHandle, 0)
        if (textureHandle[0] != 0) {
            val options = BitmapFactory.Options()
            options.inScaled = false // No pre-scaling

            // Read in the resource
            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])

            // Set filtering
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST
            )
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_NEAREST
            )

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle()
        }
        if (textureHandle[0] == 0) {
            throw RuntimeException("Error loading texture.")
            Log.i(TAG,"Error loading Texture")
        }
        return textureHandle[0]
    }
}


