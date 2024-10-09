class Player(val name: String = "Player", var attackPower: Double, var health: Double, var armor: Double) {
    val potions = mutableListOf<Potions>()

    fun attackEnemy(enemy: Enemy) {
        enemy.health -= attackPower
    }

    fun showStats() {
        println("You have ${health}hp and $attackPower attack power.")
    }

    fun usePotion(potion: Potions) {
        if (potions.contains(potion)) {
            when (potion) {
                Potions.HEALTH -> {
                    health = GameConstants.PLAYER_HEALTH
                    println("You used a health potion. Now your health is max.($health HP)")
                    potions.remove(potion)
                }

                Potions.STRENGTH -> {
                    attackPower *= 2
                    println("You used a strength potion. Now your power attack increased to double.($attackPower per attack)")
                    potions.remove(potion)
                }
                else -> {}
            }
        } else {
            println("You don't have this potion in your inventory!")
        }
    }

    fun showInventory() {
        val heathCount = potions.filter { it == Potions.HEALTH }.count()
        val strengthCount = potions.filter { it == Potions.STRENGTH }.count()
        println("You have $heathCount health potions and $strengthCount strength potions")
    }

    fun addPotion(potion: Potions) {
        potions.add(potion)
    }
}