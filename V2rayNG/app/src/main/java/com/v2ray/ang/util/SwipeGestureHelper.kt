package com.v2ray.ang.util

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

object SwipeGestureHelper {

    fun create(context: android.content.Context, onSwipeLeft: () -> Unit, onSwipeRight: () -> Unit): GestureDetector {
        return GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                if (e1 == null) return false
                val diffX = e2.x - e1.x
                val diffY = e2.y - e1.y
                if (abs(diffX) > abs(diffY) && abs(diffX) > 120 && abs(velocityX) > 200) {
                    if (diffX < 0) onSwipeLeft() else onSwipeRight()
                    return true
                }
                return false
            }
        })
    }
}
