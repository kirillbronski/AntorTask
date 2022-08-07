package com.bronski.android.antortask.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bronski.android.antortask.R
import com.bronski.android.antortask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setBottomNavigationListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setBottomNavigationListener() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.usersFragment -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.usersFragment)
                    true
                }
                R.id.manageFragment -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.manageFragment)
                    true
                }
                R.id.infoFragment -> {
                    findNavController(R.id.nav_host_main).navigate(R.id.infoFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}