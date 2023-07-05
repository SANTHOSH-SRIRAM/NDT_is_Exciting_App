package com.example.ndt_is_exciting_app.resources

enum class GridSize (val gridSize :Int) {
    EASY(9),
    MEDIUM(16),
    HARD(25);

    fun getWidth() : Int{
        return when (this) {
            EASY -> 3
            MEDIUM -> 4
            HARD -> 5
        }
    }

    fun getHeight():Int{
        return gridSize / getWidth()
    }

//    fun getNumPairs():Int {
//        return gridSize/2
//    }
}