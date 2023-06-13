package com.example.quizzapp.leaderboardDB

import PlayerScoreDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerScore::class], version = 2)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun playerScoreDao(): PlayerScoreDao
}
