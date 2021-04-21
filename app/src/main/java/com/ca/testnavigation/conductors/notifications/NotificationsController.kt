package com.ca.testnavigation.conductors.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bluelinelabs.conductor.Controller
import com.ca.testnavigation.R

class NotificationsController : Controller() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "This is notifications"

        return root
    }
}