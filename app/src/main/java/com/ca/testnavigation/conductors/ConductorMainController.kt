package com.ca.testnavigation.conductors

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.ca.testnavigation.R
import com.ca.testnavigation.conductors.dashboard.DashboardController
import com.ca.testnavigation.conductors.home.HomeController
import com.ca.testnavigation.conductors.notifications.NotificationsController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * This is the base implementation of a [Controller] which works hand in hand with the [BottomNavigationView].
 *
 * If one item in the [BottomNavigationView] we do three things:
 * * Save the current [Router.saveInstanceState] in the [routerStates] with the [BottomNavigationView.getSelectedItemId] as key. See [saveStateFromCurrentTab]
 * * Clear the current [Router] hierachy and backstack and everything (cleanup). See [clearStateFromChildRouter]
 * * Try to restore the [Router.restoreInstanceState] with the saved state contains in [routerStates]. See [tryToRestoreStateFromNewTab] and [onNavigationItemSelected]
 *
 * The main idea came from [this PR](https://github.com/bluelinelabs/Conductor/pull/316).
 */
private const val ROUTER_STATES_KEY = "STATE"

class ConductorMainController: Controller(), BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * This will hold all the information about the tabs.
     *
     * This needs to be a var because we have to reassign it in [onRestoreInstanceState]
     */
    private var routerStates = SparseArray<Bundle>()

    private lateinit var childRouter: Router

    /**
     * This is the current selected item id from the [BottomNavigationView]
     */
    @IdRes
    private var currentSelectedItemId: Int = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        val view = inflater.inflate(R.layout.conductor_main_controller, container, false)

        val childContainer = view.findViewById<ViewGroup>(R.id.conductor_container)
        childRouter = getChildRouter(childContainer)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.nav_conductor_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // We have not a single bundle/state saved.
        // Looks like this [HomeController] was created for the first time
        if (routerStates.size() == 0) {
            // Select the first item
            currentSelectedItemId = R.id.navigation_home
            childRouter.setRoot(RouterTransaction.with(HomeController()))
        } else {
            // We have something in the back stack. Maybe an orientation change happen?
            // We can just rebind the current router
            childRouter.rebindIfNeeded()
        }

        return view
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Save the state from the current tab so that we can restore it later - if needed
        saveStateFromCurrentTab(currentSelectedItemId)

        currentSelectedItemId = item.itemId

        // Clear all the hierarchy and backstack from the router. We have saved it already in the [routerStates]
        clearStateFromChildRouter()
        // Try to restore the state from the new selected tab.
        val bundleState = tryToRestoreStateFromNewTab(currentSelectedItemId)

        if (bundleState is Bundle) {
            // We have found a state (hierarchy/backstack etc.) and can just restore it to the [childRouter]
            childRouter.restoreInstanceState(bundleState)
            childRouter.rebindIfNeeded()
            return true
        }

        // There is no state (hierarchy/backstack etc.) saved in the [routerBundles].
        // We have to create a new [Controller] and set as root
        return when (item.itemId) {
            R.id.navigation_home -> {
                childRouter.setRoot(RouterTransaction.with(HomeController()))
                true
            }
            R.id.navigation_dashboard -> {
                childRouter.setRoot(RouterTransaction.with(DashboardController()))
                true
            }
            R.id.navigation_notifications -> {
                childRouter.setRoot(RouterTransaction.with(NotificationsController()))
                true
            }
            else -> false
        }
    }

    /**
     * Try to restore the state (which was saved via [saveStateFromCurrentTab]) from the [routerStates].
     *
     * @return either a valid [Bundle] state or null if no state is available
     */
    private fun tryToRestoreStateFromNewTab(itemId: Int): Bundle? {
        return routerStates.get(itemId)
    }

    /**
     * This will clear the state (hierarchy/backstack etc.) from the [childRouter] and goes back to root.
     */
    private fun clearStateFromChildRouter() {
        childRouter.setPopsLastView(true); /* Ensure the last view can be removed while we do this */
        childRouter.popToRoot();
        childRouter.popCurrentController();
        childRouter.setPopsLastView(false);
    }

    /**
     * This will save the current state of the tab (hierarchy/backstack etc.) from the [childRouter] in a [Bundle]
     * and put it into the [routerStates] with the tab id as key
     */
    private fun saveStateFromCurrentTab(itemId: Int) {
        val routerBundle = Bundle()
        childRouter.saveInstanceState(routerBundle)
        routerStates.put(itemId, routerBundle)
    }

    /**
     * Save our [routerStates] into the instanceState so we don't loose them on orientation change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        saveStateFromCurrentTab(currentSelectedItemId)
        outState.putSparseParcelableArray(ROUTER_STATES_KEY, routerStates)
        super.onSaveInstanceState(outState)
    }

    /**
     * Restore our [routerStates]
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.getSparseParcelableArray<Bundle>(ROUTER_STATES_KEY) != null){
            routerStates = savedInstanceState.getSparseParcelableArray(ROUTER_STATES_KEY)!!
        }
    }
}