package listener

import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.StatusChangeEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ShardingManager(dotenv: Dotenv) {
    private val logger: Logger = LoggerFactory.getLogger("ShardManager")
    private val token: String = dotenv["TOKEN"]
    private val shardCount: Int = dotenv["SHARD_COUNT"].toInt()
    private val builder = DefaultShardManagerBuilder.createDefault(token)

    init {
        logger.info("Starting shard manager...")

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false)

        // Register the listeners
        builder.addEventListeners(ReadyListener(), CustomEventListener())

        builder.setShardsTotal(shardCount)

        val shardManager = builder.build()

        logger.info("Shard manager started with {} shards", shardCount)

        // Add shutdown hook to properly shutdown the shard manager
        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("Shutting down shard manager...")
            shardManager.shutdown()
            logger.info("Shard manager shut down successfully")
        })
    }
}

class CustomEventListener : EventListener {
    private val logger: Logger = LoggerFactory.getLogger("CustomEventListener")

    override fun onEvent(event: GenericEvent) {
        if (event is StatusChangeEvent) {
            val shard = event.jda
            val status = event.newStatus
            logger.info("Shard {} changed status to {}", shard.shardInfo.shardId, status)
        }
    }
}
