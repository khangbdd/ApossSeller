package com.example.apossseller.uicontroler.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apossseller.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
    }
}