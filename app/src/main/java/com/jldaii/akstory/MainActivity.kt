package com.jldaii.akstory

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.jldaii.akstory.helper.*


class MainActivity : AppCompatActivity() {

    val llContent by lazy {
        findViewById<FrameLayout>(R.id.container)
    }
    val rvMain by lazy {
        findViewById<RelativeLayout>(R.id.rv_main)
    }

    val mBottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
    }

    val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    //    val navView by lazy {
//        findViewById<NavigationView>(R.id.nav_view)
//    }
    val mDrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initBottomNavView()
        initPermission()

    }

    private fun initViews() {
        setSupportActionBar(toolbar)
//        toolbar.visibility = View.GONE
        //取消toolbar的标题
//        supportActionBar?.setDisplayShowTitleEnabled(false)

        initRvMain()

    }

    private fun initRvMain() {
        val LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
        )
        if (toolbar.visibility == View.GONE) {
            LayoutParams.setMargins(rvMain.marginLeft, 0, rvMain.marginRight, rvMain.marginBottom)
        } else {
            LayoutParams.setMargins(
                rvMain.marginLeft,
                android.R.attr.actionBarSize,
                rvMain.marginRight,
                rvMain.marginBottom
            )
        }
    }

    private fun initBottomNavView() {
        mBottomNavigationView.apply {
            menu.getItem(navPosition.position)
            setOnItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
        }

    }

    private fun initPermission() {
        val permissionList: MutableList<String> = ArrayList()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!permissionList.isEmpty()) {
            val permissions = permissionList.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, 1)
        } else {
            finish()
        }
    }


    // 切换fragment
    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            supportFragmentManager.switchFragment(it, navPosition.getTag()) // Extension function
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag())
            ?: position.createFragment()
    }
}