package com.example.vpndetector.feature

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vpndetector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {
            getPostButton.setOnClickListener {
                Toast.makeText(applicationContext, "get post clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
