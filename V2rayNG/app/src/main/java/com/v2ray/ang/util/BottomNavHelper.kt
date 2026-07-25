package com.v2ray.ang.util

import android.app.Activity
import android.content.Intent
import android.view.GestureDetector
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.v2ray.ang.R
import com.v2ray.ang.ui.MainActivity
import com.v2ray.ang.ui.ServerListActivity
import com.v2ray.ang.ui.SettingsActivity
import com.v2ray.ang.ui.SubSettingActivity
import com.v2ray.ang.ui.UserAssetActivity

object BottomNavHelper {

    private val order = listOf(R.id.nav_home, R.id.nav_servers, R.id.nav_subs, R.id.nav_assets, R.id.nav_settings)

    fun setup(activity: Activity, bottomNav: BottomNavigationView, currentId: Int) {
        bottomNav.selectedItemId = currentId
        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == currentId) {
                return@setOnItemSelectedListener true
            }
            val target: Class<*>? = when (item.itemId) {
                R.id.nav_home -> MainActivity::class.java
                R.id.nav_servers -> ServerListActivity::class.java
                R.id.nav_subs -> SubSettingActivity::class.java
                R.id.nav_assets -> UserAssetActivity::class.java
                R.id.nav_settings -> SettingsActivity::class.java
                else -> null
            }
            if (target != null) {
                val intent = Intent(activity, target)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
            }
            true
        }
    }

    fun refresh(activity: Activity, viewId: Int, currentId: Int) {
        activity.findViewById<BottomNavigationView>(viewId)?.selectedItemId = currentId
    }

    fun createSwipeDetector(activity: Activity, currentId: Int): GestureDetector {
        return SwipeGestureHelper.create(activity, onSwipeLeft = { navigateRelative(activity, currentId, 1) }, onSwipeRight = { navigateRelative(activity, currentId, -1) })
    }

    private fun navigateRelative(activity: Activity, currentId: Int, dir: Int) {
        val idx = order.indexOf(currentId)
        val newIdx = idx + dir
        if (newIdx !in order.indices) return
        val target: Class<*> = when (order[newIdx]) {
            R.id.nav_home -> MainActivity::class.java
            R.id.nav_servers -> ServerListActivity::class.java
            R.id.nav_subs -> SubSettingActivity::class.java
            R.id.nav_assets -> UserAssetActivity::class.java
            R.id.nav_settings -> SettingsActivity::class.java
            else -> return
        }
        val intent = Intent(activity, target)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        activity.startActivity(intent)
        if (dir > 0) activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        else activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
