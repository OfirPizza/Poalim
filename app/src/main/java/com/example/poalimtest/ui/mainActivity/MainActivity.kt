package com.example.poalimtest.ui.mainActivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.poalimtest.R
import com.example.poalimtest.connactivity.ConnectivityRepoImpl.ConnectionState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModels()
        initBottomNavigationController()
    }

    private fun iniViewModels() {
        viewModel.isNetworkAvailableLiveData.observe(this@MainActivity, Observer { onNetworkStatusChanged(it) })
    }

    private fun onNetworkStatusChanged(connectionState: ConnectionState) {
        when (connectionState) {
            ConnectionState.CONNECTED -> onConnectionResumed()
            ConnectionState.LOST -> onConnectionLost()
        }
    }

    private fun onConnectionLost() {
        bottom_navigation_view.visibility = View.GONE
        connectivity_view.visibility = View.VISIBLE
    }

    private fun onConnectionResumed() {
        bottom_navigation_view.visibility = View.VISIBLE
        connectivity_view.visibility = View.GONE
    }

    private fun initBottomNavigationController() {
        val navController = Navigation.findNavController(this, R.id.main_nav_host)
        bottom_navigation_view.setNavController(navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeIfNeeded()
    }

    private fun closeIfNeeded() {
        if (!connectivity_view.isVisible) return
        finish()
    }
}