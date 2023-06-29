import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

fun main() {

    // Check if .env file exists
    val envFile = File(".env")
    if (!envFile.exists()) {
        try {
            // Generate .env file
            FileWriter(envFile).use {
                it.write("TOKEN=\n")
                //it.write("SHARD_COUNT=\n")
                it.write("ACTIVITY_TYPE=\n")
                it.write("ACTIVITY_DETAIL=\n")
            }
            println(".env file generated successfully.")
        } catch (e: IOException) {
            println("Failed to generate .env file: ${e.message}")
        }
    }

    val dotenv: Dotenv = dotenv()

    val token: String = dotenv["TOKEN"]
    val builder = JDABuilder.createDefault(token)

    // Disable parts of the cache
    builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
    // Enable the bulk delete event
    builder.setBulkDeleteSplittingEnabled(false)
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

    // Register the listeners
    builder.addEventListeners(ReadyListener())
    builder.addEventListeners(MessageListener())

    // Register slash commands
    //val slashCommandHandler = SlashCommandHandler(builder)
    //slashCommandHandler.registerCommands()

    builder.build()
}
