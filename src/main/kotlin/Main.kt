import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.utils.cache.CacheFlag

fun main() {
    val dotenv: Dotenv = dotenv()
    val token: String = dotenv["TOKEN"]
    val builder = JDABuilder.createDefault(token)

    // Disable parts of the cache
    builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
    // Enable the bulk delete event
    builder.setBulkDeleteSplittingEnabled(false)
    // Set activity (like "playing Something")
    builder.setActivity(Activity.watching("Your Mom"))

    // Register the listeners
    builder.addEventListeners(ReadyListener())
    builder.addEventListeners(MessageListener())

    builder.build()
}