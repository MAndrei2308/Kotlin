package com.internship.dwd.model

import com.internship.dwd.constants.GameConstants

class Board {
    var board = Array(GameConstants.BOARD_ROWS) { rowIndex ->
        Array(GameConstants.BOARD_COLUMNS) { columnIndex ->
            Room(rowIndex = rowIndex, columnIndex = columnIndex, content = null)
        }
    }

    init {
        board[1][1] = board[1][1].copy(content = Potion.HEALTH)
        board[2][2] = board[2][2].copy(content = Potion.STRENGTH)
        board[1][4] = board[1][4].copy(content = Enemy.Skeleton())
        board[2][1] = board[2][1].copy(content = Enemy.Goblin())
        board[4][4] = board[4][4].copy(content = Enemy.Orc())
        board[0][2] = board[0][2].copy(content = Enemy.Troll())
    }
}