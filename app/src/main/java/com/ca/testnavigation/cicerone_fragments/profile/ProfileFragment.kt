package com.ca.testnavigation.cicerone_fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ca.testnavigation.CiceroneActivity
import com.ca.testnavigation.R
import com.ca.testnavigation.cicerone_fragments.BaseFragment

class ProfileFragment : BaseFragment() {

    private lateinit var dashboardViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_profile)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun createFragment(factory: FragmentFactory): Fragment = ProfileFragment()

    override fun onBackPressed() {
        super.onBackPressed()
        CiceroneActivity.router.exit()
    }

}