package com.example.minimash.Games.TicTacToc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimash.Games.TicTacToc.models.Board
import com.example.minimash.Games.TicTacToc.models.BoardState
import com.example.minimash.Games.TicTacToc.models.Cell
import com.example.minimash.Games.TicTacToc.models.CellState
import kotlinx.coroutines.delay

class TicWithAIViewModel : ViewModel() {
    private var board  = Board()
    val liveBoard = MutableLiveData(board)

    private fun updateBoard(){
        liveBoard.value = board
    }

    fun cellClicked(cell: Cell){
        board.setCell(cell, CellState.Cross)
        updateBoard()

        if(board.boardState == BoardState.INCOMPLETE){
            aiTurn()
        }

    }
    fun resetBoard(){
        board.resetBoard()
        updateBoard()
    }

    private fun aiTurn(){
        val couldAIWin = board.findNextWin(CellState.Circle)
        val couldPlayerWin = board.findNextWin(CellState.Cross)
        when {
            couldAIWin != null -> board.setCell(couldAIWin, CellState.Circle)
            couldPlayerWin != null -> board.setCell(couldPlayerWin, CellState.Circle)
            board.setCell(Cell.CENTER_CENTER, CellState.Circle) -> Unit
            else -> do{

                val cell = Cell.values().random()
                val placeCell = board.setCell(cell,CellState.Circle)

            }while (!placeCell)
        }

        updateBoard()
    }
}