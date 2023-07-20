package com.example.ndt_is_exciting_app.open_gl_interface

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.opengl.Matrix.scaleM
import android.util.Log
import com.example.ndt_is_exciting_app.openGLPackageTag
import com.example.ndt_is_exciting_app.resources.drawSelectionBoxes
import com.example.ndt_is_exciting_app.resources.screenHeight
import com.example.ndt_is_exciting_app.resources.screenWidth
import com.example.ndt_is_exciting_app.resources.translate
import com.example.ndt_is_exciting_app.resources.updateShapes
import com.example.ndt_is_exciting_app.resources.zoom
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyGLRenderer(private val context : Context) : GLSurfaceView.Renderer {


    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)
    private val scaleMatrix = FloatArray(16)

    var angle = 0.0f


    private lateinit var mTriangle: Triangles
    private lateinit var mSquare: Squares
    private lateinit var vertLine : Lines
    private lateinit var constLine :ConstantThicknessLines
    private lateinit var hollowRect : HollowRect
    private lateinit var mTexture: Textures
    private lateinit var mPoint : Point
    private lateinit var testSquare : HollowRect
    private lateinit var mTexturing :Texturing
    companion object{
        var TAG = "dbug GLRenderer"
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        Log.i(openGLPackageTag,"onSurfaceCreated")

        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

        mTexture = Textures(context)
        mPoint = Point()

        setUpShapes()
    }

    override fun onDrawFrame(unused: GL10) {
        Log.i(openGLPackageTag,"onDrawFrame")

        val scratch = FloatArray(16)
        val scratch2 = FloatArray(16)

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)
        Matrix.setLookAtM(viewMatrix, 0, translate[0], translate[1], 5f, translate[0], translate[1], 0f, 0f, 1.0f, 0.0f)
        Log.i(TAG, translate.contentToString())

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        // calculate for Rotation and Zoom
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)
        scaleM(scratch,0,zoom,zoom,zoom)

        // Draw triangle and Shapes
        //mTriangle.draw(scratch)
        //mTexture.draw(scratch)
        drawShapes(scratch)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        screenHeight = height
        screenWidth = width
        val ratio: Float = width.toFloat() / height.toFloat()
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

        Log.i(openGLPackageTag,"onSurfaceChanged")
    }

    private fun setUpShapes(){
        vertLine = Lines()
        constLine = ConstantThicknessLines(floatArrayOf(0.00f, 0.5f, 0f, 0.0f, 0.0f, 0f))
        constLine.setVerts(0.5f, -0.5f, 0f, -0.5f, 0.0f, 0f)
        //constLine.SetColor(0.0f, 1.0f, 1.0f, 1.0f)

        hollowRect = HollowRect()
        //mLine = Line()
        mSquare = Squares()
        testSquare = HollowRect(floatArrayOf(0.8f,0.8f,0.0f,0.8f,0.6f,0.0f,0.6f,0.6f,0.0f,0.6f,0.8f,0.0f))
        mSquare.setSquareCoords(floatArrayOf(
            -0.5f,  0.5f, 0.0f,      // top left
            -0.5f, -0.5f, 0.0f,      // bottom left
            0.8f, -0.5f, 0.0f,      // bottom right
            1.0f,  0.5f, 0.0f
        ))
        mTexture = Textures(context)
        mTriangle = Triangles()
    }

    private fun drawShapes(vPMatrix: FloatArray){
        // Draw shape
//        mTriangle.draw(vPMatrix)
        //mLine.draw()
//        vertLine.draw()
//        mTexture.draw(vPMatrix)
//        constLine.draw(vPMatrix)
//        hollowRect.draw(vPMatrix)
//        mPoint.draw(vPMatrix)
        testSquare.draw(vPMatrix)

//        mSquare.draw(vPMatrix)
//        mTriangle.draw(vPMatrix)
        updateShapes()
        drawSelectionBoxes(vPMatrix)
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

