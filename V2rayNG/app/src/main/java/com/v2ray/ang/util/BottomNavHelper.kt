package com.v2ray.ang.util

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.v2ray.ang.R
import com.v2ray.ang.ui.MainActivity
import com.v2ray.ang.ui.ServerListActivity
import com.v2ray.ang.ui.SettingsActivity
import com.v2ray.ang.ui.SubSettingActivity
import com.v2ray.ang.ui.UserAssetActivity

object BottomNavHelper {

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
    fun refresh(activity: Activity, viewId: Int, currentId: Int) { activity.findViewById<BottomNavigationView>(viewId)?.selectedItemId = currentId }
}
