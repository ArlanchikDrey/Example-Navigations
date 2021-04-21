package com.ca.testnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ca.testnavigation.cicerone_fragments.dashboard.DashboardFragment
import com.ca.testnavigation.cicerone_fragments.home.HomeFragment
import com.ca.testnavigation.cicerone_fragments.notifications.NotificationsFragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView


class CiceroneActivity : AppCompatActivity() {

    companion object{
        private val cicerone = Cicerone.create()
        val router get() = cicerone.router
        val navigatorHolder get() = cicerone.getNavigatorHolder()
    }
    private val navigator by lazy { AppNavigator(this, R.id.container) }

    private val fragments : HashMap<String, Fragment> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cicerone)
        val bottomView: BottomNavigationView = findViewById(R.id.nav_view_cicerone)

        fragments["dashboardFragment"] = DashboardFragment()
        fragments["homeFragment"] = HomeFragment()
        fragments["notificationsFragment"] = NotificationsFragment()

        loadFragment(fragments["homeFragment"])
        bottomView.selectedItemId = R.id.navigation_home

        bottomView.setOnNavigationItemSelectedListener {
            var fragment:Fragment? = null

            when(it.itemId){
                R.id.navigation_home -> {fragment = fragments["homeFragment"];loadFragment(fragment)}
                R.id.navigation_dashboard -> {fragment = fragments["dashboardFragment"];loadFragment(fragment)}
                R.id.navigation_notifications -> {fragment = fragments["notificationsFragment"];loadFragment(fragment)}
                else -> loadFragment(fragment ?: fragments["homeFragment"])
            }
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}