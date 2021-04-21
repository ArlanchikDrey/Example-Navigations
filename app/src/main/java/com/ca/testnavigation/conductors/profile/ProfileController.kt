package com.ca.testnavigation.conductors.profile

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
import com.ca.testnavigation.conductors.home.HomeController

class ProfileController : Controller() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_profile)
        textView.text = "This is profile"

        return root
    }
}