package com.example.painter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val canvaView = PowierzchniaRysunku(this)
        canvaView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canvaView.contentDescription = getString(R.string.desc)
        setContentView(canvaView)
    }
}