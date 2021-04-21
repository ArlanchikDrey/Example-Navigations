package com.ca.testnavigation.cicerone_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseFragment: Fragment(), FragmentScreen {
    open fun onBackPressed() {}
}