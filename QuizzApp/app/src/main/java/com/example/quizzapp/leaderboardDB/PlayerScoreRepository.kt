import com.example.quizzapp.leaderboardDB.PlayerScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerScoreRepository(private val playerScoreDao: PlayerScoreDao) {
    suspend fun insertPlayerScore(playerScore: PlayerScore) {
        withContext(Dispatchers.IO) {
            playerScoreDao.insertPlayerScore(playerScore)
        }
    }

    suspend fun getTopPlayerScores(): List<PlayerScore> {
        return withContext(Dispatchers.IO) {
            playerScoreDao.getTopPlayerScores()
        }
    }
}
