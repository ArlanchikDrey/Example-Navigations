package com.ca.testnavigation

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.ca.testnavigation.conductors.ConductorMainController

class ConductorActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conductor)
        val viewGroup: ViewGroup = findViewById(R.id.changeHandlerFrameLayout)

        router = Conductor.attachRouter(this, viewGroup, savedInstanceState)

        if(!router.hasRootController()){
            router.setRoot(RouterTransaction.with(ConductorMainController()))
        }
    }

    override fun onBackPressed() {
        if(!router.handleBack()){
            super.onBackPressed()
        }
    }
}