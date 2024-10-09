class Board() {
    val rows: Int = GameConstants.BOARD_ROWS
    val columns: Int = GameConstants.BOARD_COLUMNS
    var board = Array(5) { Array(5) { Room(null, null) } }

    init {
        board[0][0] = Room(null, null) // Empty room - starting room
        board[1][1] = Room(potion = Potions.HEALTH) // Health potion
        board[2][2] = Room(potion = Potions.STRENGTH) // Strength potion
        board[1][4] =
            Room(Enemy("Skeleton", GameConstants.SKELETON_ATTACK, GameConstants.SKELETON_HEALTH)) // skeleton's room
        board[2][1] = Room(Enemy("Goblin", GameConstants.GOBLIN_ATTACK, GameConstants.GOBLIN_HEALTH)) // goblin's room
        board[4][4] = Room(Enemy("Orc", GameConstants.ORC_ATTACK, GameConstants.ORC_HEALTH)) // orc's room
        board[0][2] = Room(Enemy("Troll", GameConstants.TROLL_ATTACK, GameConstants.TROLL_HEALTH)) // troll's room
    }

    fun getRoom(row: Int, column: Int): Room? {
        if ((row in 0..4) && (column in 0..4)) {
            return board[row][column]
        } else return null
    }
}