package com.example.minimash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minimash.Games.BrickBreaker.BrickBreaker
import com.example.minimash.Games.MemoryTile.MTGameplay
import com.example.minimash.Games.MemoryTile.SelectDifficult
import com.example.minimash.Games.RockPaperScissor.RPSGameplay
import com.example.minimash.Games.TicTacToc.TicModeSelect
import com.example.minimash.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView27.paint?.isUnderlineText = true

        val tic = Intent(this, TicModeSelect::class.java)
        val memroy = Intent(this, SelectDifficult::class.java)
        val rps = Intent(this, RPSGameplay::class.java)
        val brick = Intent(this, BrickBreaker::class.java)

        binding.tic.setOnClickListener {
            startActivity(tic)
        }
        binding.memory.setOnClickListener {
            startActivity(memroy)
        }
        binding.rps.setOnClickListener {
            startActivity(rps)
        }
        binding.brick.setOnClickListener {
            startActivity(brick)
        }
        binding.button2.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

    }
}