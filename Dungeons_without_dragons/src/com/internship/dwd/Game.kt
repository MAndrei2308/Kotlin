package com.internship.dwd

import com.internship.dwd.constants.GameConstants
import com.internship.dwd.model.*
import com.internship.dwd.util.CLI
import java.util.Random

class Game(
    val player: Player,
    val board: Board
) {
    private var playerRow = 0
    private var playerColumn = 0

    fun playMove(command: Command) {
        when (command) {
            Command.Move -> processMove()
            Command.North -> playerRow--
            Command.South -> playerRow++
            Command.East -> playerColumn++
            Command.West -> playerColumn--
            Command.Attack -> TODO()
            Command.Inventory -> showInventory()
            Command.Health -> TODO()
            Command.Strength -> TODO()
            Command.Back -> TODO()
            Command.Stats -> showStats()
            Command.Quit -> TODO()
        }
    }

    private fun getPossibleMoveCommands(): Set<Command> = buildSet {
        if (playerRow > 0) add(Command.North)
        if (playerRow < GameConstants.BOARD_ROWS - 1) add(Command.South)
        if (playerColumn > 0) add(Command.West)
        if (playerRow < GameConstants.BOARD_COLUMNS - 1) add(Command.East)
    }

    private fun processMove() {
        val possibleMoveCommands = getPossibleMoveCommands()

        print(
            possibleMoveCommands.joinToString(
                prefix = "what is your move? (",
                postfix = "): ",
                transform = { command -> command.name.lowercase() }
            )
        )

        var attemptedMoveCommand: Command?
        do {
            attemptedMoveCommand = CLI.readCommand()

            when {
                Command.moveCommands.contains(attemptedMoveCommand) -> println("Invalid move! Please try again.")
                else -> println("Unknown move! Please try again.")
            }
        } while (!possibleMoveCommands.contains(attemptedMoveCommand))

        playMove(attemptedMoveCommand!!)

        when (val roomContent = (getCurrentRoom() ?: return).content) {
            is Enemy -> processEnemyEncountered(enemy = roomContent)
            is Potion -> processFoundPotion(potion = roomContent)
            else -> println("Empty room.")
        }
    }

    private fun processEnemyEncountered(enemy: Enemy) {
        print("This is ${enemy.displayName}'s room! ")
        player.showStats()

        var chosenToFight = false
        while (!chosenToFight) {
            print("You can use a potion before fight or fight directly. (attack, inventory): ")
            val option = readlnOrNull()?.trim()

            when (option) {
                "attack" -> {
                    while (player.health > 0 && enemy.health > 0) {
                        player.attackEnemy(enemy)
                        println("${player.name}: ${player.health}hp ------ ${enemy.displayName}: ${enemy.health}hp")

                        if (enemy.health > 0) {
                            enemy.attackPlayer(player)
                            println("${player.name}: ${player.health}hp ------ ${enemy.displayName}: ${enemy.health}hp")
                        }
                    }
                    if (player.health > 0) {
                        processWonFightAftermath(enemy = enemy)

                        if (enemy is Enemy.Troll) {
                            break
                        }

                        clearCurrentRoom()
                    }

                    chosenToFight = true
                }

                "inventory" -> {
                    playMove(Command.Inventory)
                    chosenToFight = false
                }
            }
        }
    }

    private fun processWonFightAftermath(enemy: Enemy) {
        when (enemy) {
            is Enemy.Skeleton -> {
                println("Congrats, you defeated the skeleton and received a potion")
                if (Random().nextInt(2) == 1) {
                    println("A health potion added in your inventory.")
                    player.addPotion(Potion.HEALTH)
                } else {
                    println("A strength potion added in your inventory.")
                    player.addPotion(Potion.STRENGTH)
                }
            }

            is Enemy.Goblin -> {
                println("Congrats, you defeated the goblin and received a new sword.")
                player.attackPower = 20.0
            }

            is Enemy.Orc -> {
                println("Congrats, you defeated the orc and received a armor.")
                player.armor = 5.0
            }

            is Enemy.Troll -> {
                println("Congrats, you won the game!!! You defeated the troll and recovered the treasure. You are the best!")
            }
        }
    }

    private fun processFoundPotion(
        potion: Potion
    ) {
        print("Congrats! You find a potion in this room. ")
        player.addPotion(potion)
        println("Added a ${potion.name} potion in inventory.")
        clearCurrentRoom()
    }

    private fun showInventory() {
        player.showInventory()
        var validPotion = false
        option@ while (!validPotion) {
            print("Do you what to use any of your potions? (health, strength, back): ")
            val usePotion = readlnOrNull()?.trim()
            when (usePotion) {
                "health" -> {
                    player.usePotion(Potion.HEALTH)
                    validPotion = true
                }

                "strength" -> {
                    player.usePotion(Potion.STRENGTH)
                    validPotion = true
                }

                "back" -> break@option
                else -> {
                    println("This potion doesn't exist. Try again")
                    validPotion = false
                }
            }
        }
    }

    private fun showStats() {
        player.showStats()
    }

    fun getCurrentRoom(): Room? {
        return if ((playerRow in 0..<GameConstants.BOARD_ROWS) && (playerColumn in 0..<GameConstants.BOARD_COLUMNS)) {
            board.board[playerRow][playerColumn]
        } else {
            null
        }
    }

    fun clearCurrentRoom() {
        board.board[playerRow][playerColumn] = board.board[playerRow][playerColumn].copy(content = null)
    }
}