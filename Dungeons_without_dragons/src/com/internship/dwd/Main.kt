package com.internship.dwd

import com.internship.dwd.constants.GameConstants
import com.internship.dwd.model.Board
import com.internship.dwd.model.Command
import com.internship.dwd.model.Player
import com.internship.dwd.util.CLI

fun main() {
    print("Write your nickname: ")
    val playerName = readlnOrNull()?.trim()
    println("\nHi, $playerName! Welcome to Dungeons without dragons!")

    val player = Player(
        name = playerName!!,
        attackPower = GameConstants.PLAYER_ATTACK,
        health = GameConstants.PLAYER_HEALTH,
        armor = GameConstants.PLAYER_DEFENCE
    )
    val board = Board()

    val game = Game(
        player = player,
        board = board
    )

    while (true) {
        val currentRoom = game.getCurrentRoom() ?: return
        println("You are at row=${currentRoom.rowIndex} and column=${currentRoom.columnIndex}")

        print(
            Command.mainCommands.joinToString(
                prefix = "command ",
                postfix = ": ",
                transform = { command -> command.name.lowercase() }
            )
        )
        when (val gameCommand = CLI.readCommand()) {
            Command.Quit -> {
                println("Goodbye, $playerName!")
                break
            }

            null -> println("Unknown command! Please try again.")

            else -> game.playMove(gameCommand)
        }

        if (player.health <= 0) {
            println("You lost this fight! Learn to play better and next time maybe you will win.")
            break
        }
    }
}