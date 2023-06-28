import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.entities.channel.ChannelType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MessageListener : ListenerAdapter() {
    private val logger: Logger = LoggerFactory.getLogger(MessageListener::class.java)
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            logger.info("[PM] ${event.author.name}: ${event.message.contentDisplay}")
        } else {
            logger.info(
                "[${event.guild.name}][${event.message.channel.name}] ${event.member?.effectiveName}: " +
                        event.message.contentDisplay
            )
        }
    }
}