import java.util.Random

fun main() {
    print("Write your nickname: ")
    val playerName = readLine()?.trim()
    println("\nHi, $playerName! Welcome to Dungeons without dragons!")

    val player =
        Player(playerName!!, GameConstants.PLAYER_ATTACK, GameConstants.PLAYER_HEALTH, GameConstants.PLAYER_DEFENCE)
    val board = Board()
    var playerRow = 0
    var playerColumn = 0
    var currentRoom = board.getRoom(0, 0)

    command@ while (true) {
        var isCommand = false

        while (!isCommand) {

            println("You are at row=$playerRow and column=$playerColumn")
            print("command (move, inventory, stats, quit): ")
            val command = readLine()?.trim()
            when (command?.lowercase()) {
                "move" -> {
                    print("what is your move? (north, south, east, west): ")
                    val move = readLine()?.trim()

                    when (move?.lowercase()) {
                        "north" -> playerRow--
                        "south" -> playerRow++
                        "east" -> playerColumn++
                        "west" -> playerColumn--
                        else -> println("Unknown move! Please try again.")
                    }

                    val currentRoom: Room? = board.getRoom(playerRow, playerColumn)
                    if (currentRoom != null) {
                        isCommand = true
                        val check = currentRoom.check()
                        when (check) {
                            is Enemy -> {
                                player.showStats()

                                var choosedToFight = false
                                while (!choosedToFight) {
                                    print("You can use a potion before fight or fight directly. (attack, inventory): ")
                                    val option = readLine()?.trim()

                                    when (option) {
                                        "attack" -> {
                                            while (player.health > 0 && check.health > 0) {
                                                if (player.health > 0) {
                                                    player.attackEnemy(check)
                                                    println("${player.name}: ${player.health}hp ------ ${check.name}: ${check.health}hp")
                                                }
                                                if (check.health > 0) {
                                                    check.attackPlayer(player)
                                                    println("${player.name}: ${player.health}hp ------ ${check.name}: ${check.health}hp")
                                                }
                                            }
                                            if (player.health > 0) {
                                                when (check.name.lowercase()) {
                                                    "skeleton" -> {
                                                        println("Congrats, you defeated the skeleton and received a potion")
                                                        val random: Random = Random()
                                                        if (random.nextInt(2) == 1) {
                                                            println("A health potion added in your inventory.")
                                                            player.addPotion(Potions.HEALTH)
                                                        } else {
                                                            println("A strength potion added in your inventory.")
                                                            player.addPotion(Potions.STRENGTH)
                                                        }
                                                    }

                                                    "goblin" -> {
                                                        println("Congrats, you defeated the goblin and received a new sword.")
                                                        player.attackPower = 20.0
                                                    }

                                                    "orc" -> {
                                                        println("Congrats, you defeated the orc and received a armor.")
                                                        player.armor = 5.0
                                                    }

                                                    "troll" -> {
                                                        println("Congrats, you won the game!!! You defeated the troll and recovered the treasure. You are the best!")
                                                        break@command
                                                    }
                                                }
                                                board.board[playerRow][playerColumn] = Room(null)
                                            }

                                            choosedToFight = true
                                        }

                                        "inventory" -> {
                                            player.showInventory()
                                            var validPotion = false
                                            option@ while (!validPotion) {
                                                print("Do you what to use any of your potions? (health, strength, back): ")
                                                val usePotion = readLine()?.trim()
                                                when (usePotion) {
                                                    "health" -> {
                                                        player.usePotion(Potions.HEALTH)
                                                        validPotion = true
                                                    }

                                                    "strength" -> {
                                                        player.usePotion(Potions.STRENGTH)
                                                        validPotion = true
                                                    }

                                                    "back" -> break@option
                                                    else -> {
                                                        println("This potion doesn't exist. Try again")
                                                        validPotion = false
                                                    }
                                                }
                                            }
                                            choosedToFight = false
                                        }
                                    }
                                }
                            }

                            is Potions -> {
                                player.addPotion(check)
                                println("Added a ${check.name} potion in inventory.")
                                board.board[playerRow][playerColumn] = Room(null)
                            }
                        }
                    } else {
                        println("Invalid move! Please try again.")

                        when (move?.lowercase()) {
                            "north" -> playerRow++
                            "south" -> playerRow--
                            "east" -> playerColumn--
                            "west" -> playerColumn++
                        }
                    }
                }

                "inventory" -> {
                    player.showInventory()
                    var validPotion = false
                    potion@ while (!validPotion) {
                        print("Do you what to use any of your potions? (health, strength, back): ")
                        val usePotion = readLine()?.trim()
                        when (usePotion) {
                            "health" -> {
                                player.usePotion(Potions.HEALTH)
                                validPotion = true
                            }

                            "strength" -> {
                                player.usePotion(Potions.STRENGTH)
                                validPotion = true
                            }

                            "back" -> break@potion
                            else -> {
                                println("This potion doesn't exist. Try again")
                                validPotion = false
                            }
                        }
                    }
                    isCommand = false
                }

                "stats" -> {
                    player.showStats()
                    isCommand = false
                }

                "quit" -> {
                    println("Goodbye, $playerName!")
                    break@command
                }

                else -> println("Unknown command! Please try again.")
            }

        }

        if (player.health <= 0) {
            println("You lost this fight! Learn to play better and next time maybe you will win.")
            break@command
        }
    }

}
