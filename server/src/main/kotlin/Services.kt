package eu.lauwenmark.jxfire.server.services

import eu.lauwenmark.jxfire.server.components.Controlled
import eu.lauwenmark.jxfire.server.components.findComponents
import eu.lauwenmark.jxfire.server.events.Event
import eu.lauwenmark.jxfire.server.events.EventListener
import eu.lauwenmark.jxfire.server.events.TickEvent
import eu.lauwenmark.jxfire.server.events.getEventQueue

interface Service {
    fun reset()
    fun start()
    fun stop()
    fun step()
}

private val services = HashMap<Class<out Service>, Service>()

fun registerService(service: Service) {
    services[service.javaClass] = service
    service.reset()
}

fun getService(cl : Class<out Service>) : Service {
    return services[cl] ?: throw IllegalArgumentException("That service is not registered.")
}

fun stepServices() {
    for(service in services.values) {
        service.step()
    }
}

fun startServices() {
    for (service in services.values) {
        service.start()
    }
}

fun stopServices() {
    for (service in services.values) {
        service.stop()
    }
}

fun resetServices() {
    for (service in services.values) {
        service.reset()
    }
}

class ControllerService : Service {
    override fun reset() {
        for(component in findComponents(Controlled::class.java)) {
            (component as Controlled).controller.reset()
        }
    }
    override fun start() {
        //Nothing to do
    }
    override fun stop() {
        //Nothing to do
    }
    override fun step() {
        System.out.println("Controlled: " + findComponents(Controlled::class.java).size)
        for (component in findComponents(Controlled::class.java)) {
            (component as Controlled).controller.tick(component)
        }
    }
}

class CommandMonitorService : Service, EventListener {
    override fun reset() {}
    override fun start() {
        getEventQueue("command").members.add(this)
    }
    override fun stop() {}
    override fun step() {}
    override fun eventReceived(event: Event) {
        System.out.println(event)
    }
}

class EventMonitorService : Service, EventListener {
    override fun reset() {}
    override fun start() {
        getEventQueue("main").members.add(this)
    }
    override fun stop() {}
    override fun step() {}
    override fun eventReceived(event: Event) {
        System.out.println(event)
    }
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
