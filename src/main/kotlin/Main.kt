import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import listener.ActivityStatus
import listener.ShardingManager
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import java.io.File
import java.io.FileWriter
import java.io.IOException

fun main() {

    // Check if .env file exists
    val envFile = File(".env")
    if (!envFile.exists()) {
        try {
            // Generate .env file
            FileWriter(envFile).use {
                it.write("TOKEN=\n")
                it.write("SHARD_COUNT=\n")
                it.write("ACTIVITY_TYPE=\n")
                it.write("ACTIVITY_DETAIL=\n")
            }
            println(".env file generated successfully.")
        } catch (e: IOException) {
            println("Failed to generate .env file: ${e.message}")
        }
    }

    val dotenv: Dotenv = dotenv()

    val builder = DefaultShardManagerBuilder.createDefault(dotenv["TOKEN"])

    // Create sharding manager and set activity status
    ShardingManager(dotenv)
    ActivityStatus(builder, dotenv)
}
