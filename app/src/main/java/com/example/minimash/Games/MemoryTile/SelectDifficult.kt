package com.example.minimash.Games.MemoryTile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minimash.R
import com.example.minimash.databinding.ActivitySelectDifficultBinding

class SelectDifficult : AppCompatActivity() {
    lateinit var binding: ActivitySelectDifficultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectDifficultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView25.paint?.isUnderlineText = true
        val intent = Intent(this, MTGameplay::class.java)
        val intent2 = Intent(this, MTHardGameplay::class.java)

        binding.imageView17.setOnClickListener {
            startActivity(intent)
        }
        binding.imageView18.setOnClickListener {
            startActivity(intent2)
        }
    }
}