package com.example.minimash.Games.RockPaperScissor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minimash.R
import com.example.minimash.databinding.ActivityRpsgameplayBinding
import kotlin.random.Random

class RPSGameplay : AppCompatActivity() {
    lateinit var binding: ActivityRpsgameplayBinding
    var cscore = 0
    var pscore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRpsgameplayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rock.setOnClickListener {
            check("rock")
            binding.player.setImageResource(R.drawable.rock)
        }
        binding.paper.setOnClickListener {
            check("paper")
            binding.player.setImageResource(R.drawable.paper)
        }
        binding.scissor.setOnClickListener {
            check("scissor")
            binding.player.setImageResource(R.drawable.scissor)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun check(a:String){
        var computer = ""
        var randomNumber = Random.nextInt(3)

        if(randomNumber == 0){
            computer = "rock"
            binding.computer.setImageResource(R.drawable.rock)
        }
        if(randomNumber == 1){
            computer = "paper"
            binding.computer.setImageResource(R.drawable.paper)
        }
        if(randomNumber == 2){
            computer = "scissor"
            binding.computer.setImageResource(R.drawable.scissor)
        }

        if(computer == a){
            binding.textView11.text = "DRAW"
        }

        if(computer == "rock" && a == "scissor"){
            binding.textView11.text = "Computer Win!!"
            cscore++
        }

        if(computer == "paper" && a == "rock"){
            binding.textView11.text = "Computer Win!!"
            cscore++
        }

        if(computer == "scissor" && a == "paper"){
            binding.textView11.text = "Computer Win!!"
            cscore++
        }

        if(computer == "scissor" && a == "rock"){
            binding.textView11.text = "Player Win!!"
            pscore++
        }

        if(computer == "rock" && a == "paper"){
            binding.textView11.text = "Player Win!!"
            pscore++
        }

        if(computer == "paper" && a == "scissor"){
            binding.textView11.text = "Player Win!!"
            pscore++
        }
        binding.textView12.text = "Player: "+pscore
        binding.textView13.text = cscore.toString() +" :Computer:"

    }
}