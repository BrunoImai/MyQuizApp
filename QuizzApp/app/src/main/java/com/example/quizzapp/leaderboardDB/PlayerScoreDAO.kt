import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizzapp.leaderboardDB.PlayerScore

@Dao
interface PlayerScoreDao {
    @Insert
    suspend fun insertPlayerScore(playerScore: PlayerScore)

    @Query("SELECT * FROM player_scores ORDER BY score DESC LIMIT 10")
    suspend fun getTopPlayerScores(): List<PlayerScore>
}

