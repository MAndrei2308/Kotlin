package com.internship.dwd.model

import com.internship.dwd.constants.GameConstants

class Player(val name: String = "Player", var attackPower: Double, var health: Double, var armor: Double) {
    val potions = mutableListOf<Potion>()

    fun attackEnemy(enemy: Enemy) {
        enemy.health -= attackPower
    }

    fun showStats() {
        println("You have ${health}hp and $attackPower attack power.")
    }

    fun usePotion(potion: Potion) {
        if (potions.contains(potion)) {
            when (potion) {
                Potion.HEALTH -> {
                    health = GameConstants.PLAYER_HEALTH
                    println("You used a health potion. Now your health is max.($health HP)")
                    potions.remove(potion)
                }

                Potion.STRENGTH -> {
                    attackPower *= 2
                    println("You used a strength potion. Now your power attack increased to double.($attackPower per attack)")
                    potions.remove(potion)
                }
            }
        } else {
            println("You don't have this potion in your inventory!")
        }
    }

    fun showInventory() {
        val heathCount = potions.filter { it == Potion.HEALTH }.count()
        val strengthCount = potions.filter { it == Potion.STRENGTH }.count()
        println("You have $heathCount health potions and $strengthCount strength potions")
    }

    fun addPotion(potion: Potion) {
        potions.add(potion)
    }
}