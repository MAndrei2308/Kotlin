class Room(val enemy: Enemy? = null, val potion: Potions? = null) {

    fun check(): Any? {
        when {
            enemy != null -> {
                print("This is ${enemy.name}'s room! ")
                return enemy
            }

            potion != null -> {
                print("Congrats! You find a potion in this room. ")
                return potion
            }

            else -> println("Empty room.")
        }
        return null
    }
}