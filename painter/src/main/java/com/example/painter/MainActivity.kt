package com.example.painter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Key.VISIBILITY
import androidx.fragment.app.Fragment
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private var imagesBitmap = arrayListOf<Bitmap>(
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()

        val fragmentView: View = findViewById(R.id.listFragment)
        fragmentView.visibility = View.INVISIBLE

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

        val button_BACK = findViewById<Button>(R.id.backbutton)
        button_BACK.setOnClickListener{
            drawCanvas.visibility = View.VISIBLE
            surfaceView.visibility = View.VISIBLE
            fragmentView.visibility = View.GONE
            button_BACK.visibility = View.INVISIBLE
        }

        val button_SAVE = findViewById<Button>(R.id.button5)
        button_SAVE.setOnClickListener {
            button_BACK.visibility = View.VISIBLE
            imagesBitmap[counter] = (Bitmap.createBitmap(surfaceView.mBitmapa))
            when (counter) {
                2 -> counter = 0
                else -> counter++
            }
            Toast.makeText(applicationContext,"Zapisano obraz",Toast.LENGTH_SHORT).show()
            val mBundle = Bundle()
            mBundle.putParcelableArrayList(BITMAP_KEY,imagesBitmap)

            drawCanvas.visibility = View.GONE
            surfaceView.visibility = View.GONE

            fragmentView.visibility = View.VISIBLE

            val mFragmentManager = supportFragmentManager
            val mFragment = ListFragment()
            val mFragmentTransacition = mFragmentManager.beginTransaction()
            mFragment.arguments = mBundle
            mFragmentTransacition.replace(R.id.listFragment,mFragment)
            mFragmentTransacition.commit()

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

    companion object {
        const val BITMAP_KEY = "BITMAP_KEY"
        var counter = 0
    }
}

