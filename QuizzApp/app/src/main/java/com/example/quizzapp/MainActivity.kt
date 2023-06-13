import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.quizzapp.leaderboardDB.PlayerScore
import com.example.quizzapp.leaderboardDB.QuizDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomePage(this@MainActivity)
        }
    }



    private fun showLeaderboard() {
        // TODO: Implement showing leaderboard
    }
}
