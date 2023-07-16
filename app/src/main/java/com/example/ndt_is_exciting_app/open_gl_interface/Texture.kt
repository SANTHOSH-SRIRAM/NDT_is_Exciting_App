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


class Texture (context: Context,
    private var squareCoords :FloatArray = floatArrayOf(-0.5f,  0.5f, 0.0f,      // top left
                                                        -0.5f, -0.5f, 0.0f,      // bottom left
                                                         0.5f, -0.5f, 0.0f,      // bottom right
                                                         0.5f,  0.5f, 0.0f       // top right
)) {

    companion object{
        private var TAG = "dbug Texture shape"
    }

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var drawListBuffer: ShortBuffer

    private val coordsPerVertex = 3
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
    var color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0

    private val vertexShaderCode =
    "uniform mat4 u_MVPMatrix;       // A constant representing the combined model/view/projection matrix." +
            "uniform mat4 u_MVMatrix;        // A constant representing the combined model/view matrix." +
            "" +
            "attribute vec4 a_Position;      // Per-vertex position information we will pass in." +
            "attribute vec4 a_Color;         // Per-vertex color information we will pass in." +
            "attribute vec3 a_Normal;        // Per-vertex normal information we will pass in." +
            "attribute vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in." +
            "" +
            "varying vec3 v_Position;        // This will be passed into the fragment shader." +
            "varying vec4 v_Color;           // This will be passed into the fragment shader." +
            "varying vec3 v_Normal;          // This will be passed into the fragment shader." +
            "varying vec2 v_TexCoordinate;   // This will be passed into the fragment shader." +
            "" +
            "// The entry point for our vertex shader." +
            "void main()" +
            "{" +
            "    // Transform the vertex into eye space." +
            "    v_Position = vec3(u_MVMatrix * a_Position);" +
            "" +
            "    // Pass through the color." +
            "    v_Color = a_Color;" +
            " " +
            "    // Pass through the texture coordinate." +
            "    v_TexCoordinate = a_TexCoordinate;" +
            " " +
            "    // Transform the normal's orientation into eye space." +
            "    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));" +
            " " +
            "    // gl_Position is a special variable used to store the final position." +
            "    // Multiply the vertex by the matrix to get the final point in normalized screen coordinates." +
            "    gl_Position = u_MVPMatrix * a_Position;" +
            "}"


    private val fragmentShaderCode =
        "precision mediump float;        // Set the default precision to medium. We don't need as high of a" +
                "                                // precision in the fragment shader." +
                "uniform vec3 u_LightPos;        // The position of the light in eye space." +
                "uniform sampler2D u_Texture;    // The input texture." +
                "" +
                "varying vec3 v_Position;        // Interpolated position for this fragment." +
                "varying vec4 v_Color;           // This is the color from the vertex shader interpolated across the" +
                "                                // triangle per fragment." +
                "varying vec3 v_Normal;          // Interpolated normal for this fragment." +
                "varying vec2 v_TexCoordinate;   // Interpolated texture coordinate per fragment." +
                " " +
                "// The entry point for our fragment shader." +
                "void main()" +
                "{" +
                "    // Will be used for attenuation." +
                "    float distance = length(u_LightPos - v_Position);" +
                " " +
                "    // Get a lighting direction vector from the light to the vertex." +
                "    vec3 lightVector = normalize(u_LightPos - v_Position);" +
                " " +
                "    // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are" +
                "    // pointing in the same direction then it will get max illumination." +
                "    float diffuse = max(dot(v_Normal, lightVector), 0.0);" +
                "" +
                "    // Add attenuation." +
                "    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));" +
                "" +
                "    // Add ambient lighting" +
                "    diffuse = diffuse + 0.3;" +
                " " +
                "    // Multiply the color by the diffuse illumination level and texture value to get final output color." +
                "    gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate));" +
                "  }"

    // initialize vertex byte buffer for shape coordinates

    private var mProgram: Int
    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    /** Store our model data in a float buffer.  */
    private lateinit var mCubeTextureCoordinates: FloatBuffer

    /** This will be used to pass in the texture.  */
    private var mTextureUniformHandle = 0

    /** This will be used to pass in model texture coordinate information.  */
    private var mTextureCoordinateHandle = 0

    /** Size of the texture coordinate data in elements.  */
    private val mTextureCoordinateDataSize = 2

    /** This is a handle to our texture data.  */
    private var mTextureDataHandle = 0

    private val cubeTextureCoordinateData : FloatArray = floatArrayOf(
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f,
    )

    private val vertexCount: Int = squareCoords.size / coordsPerVertex
    private val vertexStride: Int = coordsPerVertex * 4 // 4 bytes per vertex


    init {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        evaluateVertexBuffer()

        // Load the texture
        mTextureDataHandle = loadTexture(context, R.drawable.blank_fill_in)
        Log.i(TAG,"Loaded texture handle")

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram().also {

            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)

        }



    }

    fun setSquareCoords(squareCoords: FloatArray){
        this.squareCoords = squareCoords
        evaluateVertexBuffer()
    }

    fun SetColor(Red : Float,Blue : Float,Green : Float,Alpha : Float){
        color = floatArrayOf(Red,Blue,Green,Alpha)
    }

    private fun evaluateVertexBuffer(){

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


    }



    open fun draw(mvpMatrix : FloatArray) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

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
            Log.i(TAG,"triangle Coordinate Data")
            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            Log.i(TAG,"Texture handleing start ")
//            mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
//            mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
//
//            // Set the active texture unit to texture unit 0.
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//
//            // Bind the texture to this unit.
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
//
//            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
//            GLES20.glUniform1i(mTextureUniformHandle, 0);
//
//            Log.i(TAG,"Texture Handleing End")

            // get handle to shape's transformation matrix
            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)


            // Draw the two triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)


            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }

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
            Log.i(TAG,"ErrorLoading Texture")
        }
        return textureHandle[0]
    }
}
