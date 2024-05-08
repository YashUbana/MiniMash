package com.example.minimash.Games.TicTacToc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minimash.databinding.ActivityTicModeSeleteBinding

class TicModeSelect : AppCompatActivity() {
    lateinit var binding: ActivityTicModeSeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicModeSeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView9.paint?.isUnderlineText = true

        val pvp = Intent(this, TicGameplay::class.java)
        val pvc = Intent(this, TicWithAI::class.java)


        binding.imageView9.setOnClickListener {
            startActivity(pvp)
        }
        binding.imageView10.setOnClickListener {
            startActivity(pvc)
        }
    }
}