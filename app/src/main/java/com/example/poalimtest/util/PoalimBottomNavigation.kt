package com.example.poalimtest.util

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.setupWithNavController
import com.example.poalimtest.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.view_bottom_navigation.view.*

class PoalimBottomNavigation @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr), NavController.OnDestinationChangedListener {

    init {
        View.inflate(context, R.layout.view_bottom_navigation, this)
    }

    fun setNavController(controller: NavController) {
        navigation_view.setupWithNavController(controller)
        controller.addOnDestinationChangedListener(this)
    }

    private fun updateNavigation(destinationId: Int?) {
        val id = destinationId ?: return
        val navItemId = getNavItemIdByDestination(id)
        navItemId?.let {
            navigation_view.menu.findItem(navItemId).isChecked = true
            visibility = View.VISIBLE
            return
        }
        visibility = View.GONE
    }

    private fun getNavItemIdByDestination(destinationId: Int): Int? {
        return when (destinationId) {
            R.id.MovieFragment -> {
                R.id.navigation_movies
            }
            R.id.FavoriteMoviesFragment -> {
                R.id.navigation_favorite_movies
            }

            else -> null
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        updateNavigation(controller.currentDestination?.id)
    }
}