class Enemy(val name: String, val attackPower: Double, var health: Double) {
    fun attackPlayer(player: Player) {
        if (player.armor < attackPower) {
            player.health -= attackPower - player.armor
        }
    }
}