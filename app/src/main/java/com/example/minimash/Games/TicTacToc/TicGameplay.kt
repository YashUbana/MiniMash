package com.example.minimash.Games.TicTacToc

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.minimash.R
import com.example.minimash.databinding.ActivityTicGameplayBinding
import kotlin.random.Random


class TicGameplay : AppCompatActivity() {
    lateinit var binding: ActivityTicGameplayBinding
    private val combinationList = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(2, 4, 6),
        intArrayOf(2, 4, 8)
    )
    private var boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var playerTurn = 1
    private var totalSelectedBoxes = 1
    lateinit var builder:AlertDialog.Builder


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTicGameplayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        builder =  AlertDialog.Builder(this)


        binding.imageView0.setOnClickListener(clickListener("imageView0", binding.imageView0, 0))
        binding.imageView1.setOnClickListener(clickListener("imageView1", binding.imageView1, 1))
        binding.imageView2.setOnClickListener(clickListener("imageView2", binding.imageView2, 2))
        binding.imageView3.setOnClickListener(clickListener("imageView3", binding.imageView3, 3))
        binding.imageView4.setOnClickListener(clickListener("imageView4", binding.imageView4, 4))
        binding.imageView5.setOnClickListener(clickListener("imageView5", binding.imageView5, 5))
        binding.imageView6.setOnClickListener(clickListener("imageView6", binding.imageView6, 6))
        binding.imageView7.setOnClickListener(clickListener("imageView7", binding.imageView7, 7))
        binding.imageView8.setOnClickListener(clickListener("imageView8", binding.imageView8, 8))


    }

    private fun clickListener(
        imageId: String,
        view: ImageView,
        position: Int
    ): View.OnClickListener {
        return View.OnClickListener {
            if (isBoxSelectable(position))
                performAction(view, position)
        }
    }


    private fun isBoxSelectable(selectedBoxPosition: Int): Boolean {
        return boxPositions[selectedBoxPosition] == 0
    }

    private fun performAction(image: ImageView, selectedBoxPosition: Int) {
        boxPositions[selectedBoxPosition] = playerTurn

        image.setImageResource(
            if (playerTurn == 1){
                R.drawable.ximage
            } else
                R.drawable.oimage
        )

        if (checkResult()) {
            if (playerTurn == 1) {
                builder.setMessage("X Player Won!!").setPositiveButton("Play Again"){
                    dialog, which->
                    Toast.makeText(this, "X win", Toast.LENGTH_SHORT).show()
                }
                builder.show()

                restartMatch()
            } else {
                builder.setMessage("O Player Won!!").setPositiveButton("Play Again"){
                        dialog, which->
                    Toast.makeText(this, "O win", Toast.LENGTH_SHORT).show()
                }
                builder.show()
                restartMatch()
            }
        } else if (totalSelectedBoxes == 9) {
            builder.setMessage("DRAW").setPositiveButton("Play Again"){
                    dialog, which->
                Toast.makeText(this, "Its a draw", Toast.LENGTH_SHORT).show()
            }
            builder.show()

            restartMatch()
        } else {
            changePlayerTurn(if (playerTurn == 1) 2 else 1)
            totalSelectedBoxes++
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changePlayerTurn(i: Int) {
        if(playerTurn == 1){
            binding.turnResult.text = "O turn"
            binding.p1Box.setBackgroundColor(Color.WHITE)
            binding.p2Box.setBackgroundColor(Color.RED)
        }else{
            binding.turnResult.text = "X turn"
            binding.p1Box.setBackgroundColor(Color.RED)
            binding.p2Box.setBackgroundColor(Color.WHITE)
        }
        playerTurn = i
    }

    private fun checkResult(): Boolean {
        for (combination in combinationList) {
            if (boxPositions[combination[0]] == playerTurn &&
                boxPositions[combination[1]] == playerTurn &&
                boxPositions[combination[2]] == playerTurn
            ) {
                return true
            }
        }
        return false
    }

    private fun restartMatch() {
        boxPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0) //9 zero

        playerTurn = 1
        totalSelectedBoxes = 1
        for (i in 0..8) {
            val imageViewId = resources.getIdentifier("imageView$i", "id", packageName)
            val imageView = findViewById<ImageView>(imageViewId)
            imageView.setImageResource(R.drawable.white_box)
            binding.turnResult.text = "X turn"
            binding.p1Box.setBackgroundColor(Color.RED)
            binding.p2Box.setBackgroundColor(Color.WHITE)
        }
    }

    private fun computerMove() {
        val emptyBox = mutableListOf<Int>()
        for (i in boxPositions.indices) {
            if (boxPositions[i] == 0)
                emptyBox.add(i)
        }
        val randomIndex = Random.nextInt(emptyBox.size)
        val selectedBoxPosition = emptyBox[randomIndex]
//        val imageView = binding.gri
    }


}