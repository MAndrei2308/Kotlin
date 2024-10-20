package com.internship.dwd.model

// @formatter:off
enum class Command {
    Move,
        North,
        South,
        East,
        West,
        Attack,
    Inventory,
        Health,
        Strength,
        Back,
    Stats,
    Quit;

    companion object {
        val mainCommands = setOf(Move, Inventory, Stats, Quit)
        val moveCommands = setOf(North, South, East, West)
    }
}
// @formatter:on