package listener

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ReadyListener : EventListener {
    private val logger: Logger = LoggerFactory.getLogger(ReadyListener::class.java)
    override fun onEvent(event: GenericEvent) {
        if (event is ReadyEvent) {
            logger.info("API is ready!")
        }
    }
}
