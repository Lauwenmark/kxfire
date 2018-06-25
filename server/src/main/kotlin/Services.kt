package eu.lauwenmark.jxfire.server.services

import eu.lauwenmark.jxfire.server.events.TickEvent
import eu.lauwenmark.jxfire.server.events.getEventQueue

interface Service {
    fun reset()
    fun start()
    fun stop()
    fun step()
}

private val services = HashMap<Class<Service>, Service>()

fun registerService(service: Service) {
    services[service.javaClass] = service
}

fun getService(cl : Class<Service>) : Service {
    return services[cl] ?: throw IllegalArgumentException("That service is not registered.")
}

/**
 * A demo service keeping the number of ticks spent since the game initialization.
 * At each tick, the service posts a TickEvent.
 */
class TickService : Service {

    private var tick = 0L
    private var running = true

    override fun reset() {
        tick = 0
    }
    override fun start() {
        running = true
    }
    override fun stop() {
        running = false
    }
    override fun step() {
        if (running) {
            tick++
            getEventQueue("main").post(TickEvent(tick))
        }
    }
}
