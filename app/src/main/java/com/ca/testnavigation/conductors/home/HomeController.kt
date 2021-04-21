package com.ca.testnavigation.conductors.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.ca.testnavigation.CiceroneActivity
import com.ca.testnavigation.R
import com.ca.testnavigation.cicerone_fragments.BaseFragment
import com.ca.testnavigation.cicerone_fragments.home.HomeViewModel
import com.ca.testnavigation.cicerone_fragments.profile.ProfileFragment
import com.ca.testnavigation.conductors.profile.ProfileController

class HomeController : Controller() {

    private lateinit var textView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textView = root.findViewById(R.id.text_home)
        textView.text = "This is home"

        textView.setOnClickListener {
            router.pushController(RouterTransaction.with(ProfileController()))
        }

        return root
    }
}