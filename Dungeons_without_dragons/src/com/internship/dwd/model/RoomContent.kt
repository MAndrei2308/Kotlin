package com.internship.dwd.model

sealed interface RoomContent

sealed interface Enemy: RoomContent {

    val displayName: String
    var health: Double
    val dps: Double

    class Skeleton: Enemy {
        override var health: Double = 40.0
        override val dps: Double = 5.0

        override val displayName: String = "Skeleton"
    }

    class Goblin: Enemy {
        override var health: Double = 50.0
        override val dps: Double = 20.0

        override val displayName: String = "Goblin"
    }

    class Orc: Enemy {
        override var health: Double = 75.0
        override val dps: Double = 15.0

        override val displayName: String = "Orc"
    }

    class Troll: Enemy {
        override var health: Double = 161.0
        override val dps: Double = 25.0

        override val displayName: String = "Troll"
    }

    fun attackPlayer(player: Player) {
        if (player.armor < dps) {
            player.health -= dps - player.armor
        }
    }
}

enum class Potion: RoomContent {
    HEALTH,
    STRENGTH
}