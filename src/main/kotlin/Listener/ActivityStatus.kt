package listener

import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import java.util.*

class ActivityStatus(builder: DefaultShardManagerBuilder, dotenv: Dotenv) {
    init {
        // Set activity (like "playing Something")
        val activityType: String? = dotenv["ACTIVITY_TYPE"]
        val activityDetail: String = dotenv["ACTIVITY_DETAIL"]
        val activity: Activity = when (activityType?.lowercase(Locale.getDefault())) {
            "playing" -> Activity.playing(activityDetail)
            "watching" -> Activity.watching(activityDetail)
            "listening" -> Activity.listening(activityDetail)
            //"streaming" -> Activity.streaming(activityDetail, "https://twitch.tv/yourstream")
            else -> Activity.playing(activityDetail)
        }
        builder.setActivity(activity)
    }
}
