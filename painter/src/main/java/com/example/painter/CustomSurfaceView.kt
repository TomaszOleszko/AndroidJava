package com.example.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewConfiguration

class CustomSurfaceView(context: Context, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private var path = Path()

    private var mPojemnik: SurfaceHolder = holder

    private var mBlokada = Object()

    lateinit var mBitmapa: Bitmap
    private lateinit var mKanwa: Canvas

    private var drawColor = Color.RED
    private val mPaintS = Paint().apply {
        color = drawColor
        style = Paint.Style.STROKE
        strokeWidth = 2F
    }
    private val mPaintF = Paint().apply {
        color = drawColor
        style = Paint.Style.FILL
        strokeWidth = 2F
    }

    fun changePaintColor(color: Int) {
        drawColor = color
        mPaintS.color = drawColor
        mPaintF.color = drawColor
    }

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var screenHeight = 0
    private var screenWidth = 0

    init {
        isFocusable = true
        mPojemnik.addCallback(this)
        setZOrderOnTop(true)
        this.setBackgroundColor(Color.RED)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::mBitmapa.isInitialized) mBitmapa.recycle()
        mBitmapa = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mKanwa = Canvas(mBitmapa)
        mKanwa.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmapa, 0f, 0f, null)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mBitmapa = Bitmap.createBitmap(
            holder.surfaceFrame.width(),
            holder.surfaceFrame.height(),
            Bitmap.Config.ARGB_8888
        )
        mKanwa = Canvas(mBitmapa)
        mKanwa.drawColor(Color.WHITE)

        screenHeight = height
        screenWidth = width
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun clearCanvas() {
        mKanwa.drawColor(Color.WHITE)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()

        synchronized(mBlokada) {
            if (event != null) {
                motionTouchEventX = event.x
                motionTouchEventY = event.y

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStart()
                    MotionEvent.ACTION_MOVE -> touchMove()
                    MotionEvent.ACTION_UP -> touchUp()
                }
            }
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY

        mKanwa.drawCircle(currentX, currentY, 10F, mPaintF)
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            mKanwa.drawPath(path, mPaintS)
        }
        // Invalidate() is inside the touchMove() under ACTION_MOVE because there are many other
        // types of motion events passed into this listener, and we don't want to invalidate the
        // view for those.
        invalidate()
    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        mKanwa.drawCircle(currentX, currentY, 10F, mPaintF)
        path.reset()
    }

}