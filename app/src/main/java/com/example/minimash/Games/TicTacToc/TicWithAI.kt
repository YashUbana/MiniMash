package com.example.minimash.Games.TicTacToc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.minimash.Games.TicTacToc.models.Board
import com.example.minimash.Games.TicTacToc.models.BoardState
import com.example.minimash.Games.TicTacToc.models.Cell
import com.example.minimash.R
import com.example.minimash.databinding.ActivityTicWithAiBinding

class TicWithAI : AppCompatActivity() {
    lateinit var binding: ActivityTicWithAiBinding
    lateinit var builder: AlertDialog.Builder
    val vm: TicWithAIViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTicWithAiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        vm.liveBoard.observe(this, onBoardChange)
        bindButtons()

    }

    private val onBoardChange = Observer {
        board : Board ->
        updateBoardCells(board)
        updateGameStatus(board.boardState)
    }

    private fun bindButtons() = with(binding) {
        reset.setOnClickListener {vm.resetBoard()}
        imageView0.setOnClickListener { vm.cellClicked(Cell.TOP_LEFT)
            imageView0.isEnabled = false
        }
        imageView1.setOnClickListener { vm.cellClicked(Cell.TOP_CENTER)
            imageView1.isEnabled = false}
        imageView2.setOnClickListener { vm.cellClicked(Cell.TOP_RIGHT)
            imageView2.isEnabled = false}
        imageView3.setOnClickListener { vm.cellClicked(Cell.CENTER_LEFT)
            imageView3.isEnabled = false}
        imageView4.setOnClickListener { vm.cellClicked(Cell.CENTER_CENTER)
            imageView4.isEnabled = false}
        imageView5.setOnClickListener { vm.cellClicked(Cell.CENTER_RIGHT)
            imageView5.isEnabled = false}
        imageView6.setOnClickListener { vm.cellClicked(Cell.BOTTOM_LEFT)
            imageView6.isEnabled = false}
        imageView7.setOnClickListener { vm.cellClicked(Cell.BOTTOM_CENTER)
            imageView7.isEnabled = false}
        imageView8.setOnClickListener { vm.cellClicked(Cell.BOTTOM_RIGHT)
            imageView8.isEnabled = false}
    }
    private fun enableAll(){
        binding.imageView0.isEnabled = true
        binding.imageView1.isEnabled = true
        binding.imageView2.isEnabled = true
        binding.imageView3.isEnabled = true
        binding.imageView4.isEnabled = true
        binding.imageView5.isEnabled = true
        binding.imageView6.isEnabled = true
        binding.imageView7.isEnabled = true
        binding.imageView8.isEnabled = true
    }
    private fun updateBoardCells(board: Board){
        binding.imageView0.setImageResource(board.getState(Cell.TOP_LEFT).res)
        binding.imageView1.setImageResource(board.getState(Cell.TOP_CENTER).res)
        binding.imageView2.setImageResource(board.getState(Cell.TOP_RIGHT).res)
        binding.imageView3.setImageResource(board.getState(Cell.CENTER_LEFT).res)
        binding.imageView4.setImageResource(board.getState(Cell.CENTER_CENTER).res)
        binding.imageView5.setImageResource(board.getState(Cell.CENTER_RIGHT).res)
        binding.imageView6.setImageResource(board.getState(Cell.BOTTOM_LEFT).res)
        binding.imageView7.setImageResource(board.getState(Cell.BOTTOM_CENTER).res)
        binding.imageView8.setImageResource(board.getState(Cell.BOTTOM_RIGHT).res)
    }
    private fun updateGameStatus(boardState: BoardState) = when(boardState){
        BoardState.CROSS_WIN -> {
            builder.setMessage("Player Won").setPositiveButton("Play Again"){
                    dialog, which->
                Toast.makeText(this, "X win", Toast.LENGTH_SHORT).show()
                vm.resetBoard()
                enableAll()
            }
            builder.show()

        }
        BoardState.CIRCLES_WIN -> {
            builder.setMessage("Computer Won!").setPositiveButton("Play Again"){
                    dialog, which->
                Toast.makeText(this, "X win", Toast.LENGTH_SHORT).show()
                vm.resetBoard()
                enableAll()
            }
            builder.show()

        }

        BoardState.INCOMPLETE -> enableAll()



        BoardState.DRAW -> {
            builder.setMessage("DRAW").setPositiveButton("Play Again"){
                    dialog, which->
                Toast.makeText(this, "Its a draw", Toast.LENGTH_SHORT).show()
                vm.resetBoard()
                enableAll()
            }
            builder.show()

        }
    }
}