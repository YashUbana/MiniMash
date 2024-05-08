package com.example.minimash.Games.TicTacToc.models

import androidx.annotation.DrawableRes
import com.example.minimash.R

sealed class CellState(@DrawableRes val res: Int) {
    object Cross: CellState(R.drawable.ximage)
    object Circle: CellState(R.drawable.oimage)
    object Empty: CellState(R.drawable.white_box)
}