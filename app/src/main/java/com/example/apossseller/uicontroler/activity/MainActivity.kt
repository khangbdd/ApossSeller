package com.example.apossseller.uicontroler.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.apossseller.R
import com.example.apossseller.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        navController = findNavController(R.id.fragmentContainerView2)
        binding.bottomNavigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private var exit: Boolean = false


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.products -> {
                    navController.navigate(R.id.productGeneralFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.orders -> {
                    navController.navigate(R.id.orderGeneralFragment)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }
}