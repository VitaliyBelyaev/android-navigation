package com.example.android.codelabs.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFlowFragment : Fragment() {
    private var currentNavController: LiveData<NavController>? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fmt_main_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNavigationBar(view)

        val rootNavController = (activity as? MainActivity)?.rootNavController

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.apply {
            inflateMenu(R.menu.overflow_menu)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.settingsFragment) {
                    rootNavController?.navigate(R.id.settingsFragment)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun setupBottomNavigationBar(view: View) {
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        val navGraphIds = listOf(
            R.navigation.home_flow_navigation,
            R.navigation.deeplink_flow_navigation
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.main_flow_nav_host_container,
            intent = activity?.intent ?: Intent()
        )

        currentNavController = controller
    }
}
