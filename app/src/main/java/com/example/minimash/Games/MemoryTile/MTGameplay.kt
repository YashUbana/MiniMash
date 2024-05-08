package com.example.minimash.Games.MemoryTile

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.minimash.R
import com.example.minimash.databinding.ActivityMtgameplayBinding

class MTGameplay : AppCompatActivity() {
    lateinit var binding: ActivityMtgameplayBinding
    private lateinit var buttons: List<ImageView>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    lateinit var images: MutableList<Int>
    lateinit var builder: AlertDialog.Builder
    private var life = 7;
    private var points = 0;

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMtgameplayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)
        binding.textView23.paint?.isUnderlineText = true
        binding.textView22.paint?.color = Color.RED

        images = mutableListOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
        )

        images.addAll(images)
        images.shuffled()

        buttons = listOf(
            binding.imageView0,
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9,
            binding.imageView10,
            binding.imageView11,
            binding.imageView12,
            binding.imageView13,
            binding.imageView14,
            binding.imageView15,
            )

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        binding.resetGame.setOnClickListener {
            resetGame()
        }

        binding.textView22.text ="  : "+life

        buttons.forEachIndexed{index, button ->
            button.setOnClickListener {
                binding.textView23.isVisible = false
                Log.i("Memory","Button Clicked")
                updateModels(index)

                updateViews()

            }
        }

    }

    private fun updateViews() {
        Log.d("life",life.toString())
        if(life == 0){
            builder.setMessage("Game Over!!").setPositiveButton("Play Again?"){
                    dialog, which->
                resetGame()
            }
            builder.show()
        }
        cards.forEachIndexed {index, card ->
            val button = buttons[index]
            if(card.isMatched) {
                button.alpha = 0.5f
            }
            button.setImageResource(if(card.isFaceUp) card.identifier else R.drawable.white_box)
        }
    }
    private fun updateModels(position: Int) {
        val card = cards[position]
        if(card.isFaceUp){
            Log.d("lifqqe",life.toString())
            return
        }
        if(indexOfSingleSelectedCard == null){
            restoreCards()
            indexOfSingleSelectedCard = position
        }
        else{
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp

        if (cards.all { it.isMatched }) {
            builder.setMessage("Congratulations! You've won the game!").setPositiveButton("Play Again?"){
                    dialog, which->
                resetGame()
            }
            builder.show()
        }
    }
    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched)
                card.isFaceUp = false
        }
    }
    private fun checkForMatch(position1: Int, position2: Int){
        if (cards[position1].identifier == cards[position2].identifier) {

            cards[position1].isMatched = true
            cards[position2].isMatched = true
            points += 5
            life+=1
            binding.textView22.text = "  :  "+life
            binding.textView21.text =  "POINTS : "+points
        }
        else{

            life -= 1
            binding.textView22.text = "  :  "+life
            points -= 2
            binding.textView21.text =  "POINTS : "+points
        }
    }
    private fun resetGame() {
        // Reset the state of the cards
        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        buttons.forEach { it.alpha = 1.0f }
        // Shuffle the images again
        val images = mutableListOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
        )
        life = 15
        points = 0
        binding.textView21.text = "POINTS : "+points
        binding.textView22.text = "  :  "+life
        images.addAll(images)
        images.shuffle()
        // Update the cards with shuffled images
        for (i in buttons.indices) {
            cards[i].identifier = images[i]
        }
        // Reset index of single selected card
        indexOfSingleSelectedCard = null
        // Update the views
        updateViews()
    }
}