package com.example.painter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()

        val surfaceView = CustomSurfaceView(applicationContext, null)
        val drawCanvas = findViewById<LinearLayout>(R.id.CustomSurfaceView)
        drawCanvas.addView(surfaceView)

        val button_RED = findViewById<Button>(R.id.button)
        button_RED.setOnClickListener {
            surfaceView.changePaintColor(Color.RED)
        }

        val button_YEL = findViewById<Button>(R.id.button2)
        button_YEL.setOnClickListener {
            surfaceView.changePaintColor(Color.YELLOW)
        }

        val button_BLU = findViewById<Button>(R.id.button3)
        button_BLU.setOnClickListener {
            surfaceView.changePaintColor(Color.BLUE)
        }

        val button_GRE = findViewById<Button>(R.id.button4)
        button_GRE.setOnClickListener {
            surfaceView.changePaintColor(Color.GREEN)
        }

        val button_CLEAR = findViewById<ImageButton>(R.id.imageButton)
        button_CLEAR.setOnClickListener {
            surfaceView.clearCanvas()
        }

        val button_SAVE = findViewById<Button>(R.id.button5)
        button_SAVE.setOnClickListener {
            val bitmap = surfaceView.mBitmapa
            Toast.makeText(applicationContext,"Zapisano obraz",Toast.LENGTH_SHORT).show()
        }

    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)

        return returnedBitmap
    }
}