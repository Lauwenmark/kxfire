package eu.lauwenmark.jxfire.server.services

import eu.lauwenmark.jxfire.server.components.Controlled
import eu.lauwenmark.jxfire.server.components.Position
import eu.lauwenmark.jxfire.server.components.findComponents
import eu.lauwenmark.jxfire.server.events.*

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

class ControllerService private constructor(): Service {
    companion object {
        fun register() = registerService(ControllerService())
    }
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
        for (component in findComponents(Controlled::class.java)) {
            (component as Controlled).controller.tick(component)
        }
    }
}

class CommandMonitorService private constructor(): Service, EventListener {
    companion object {
        fun register() = registerService(CommandMonitorService())
    }
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

class EventMonitorService private constructor(): Service, EventListener {
    companion object {
        fun register() = registerService(EventMonitorService())
    }
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
class TickService private constructor(): Service {

    private var tick = 0L
    private var running = true

    companion object {
        fun register() = registerService(TickService())
    }
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

class MoveService private constructor(): Service, EventListener {
    private var running = false

    companion object {
        fun register() = registerService(MoveService())
    }

    override fun eventReceived(event: Event) {
        when(event) {
            is MoveUpCommand -> {
                val pos = event.entity[Position::class.java] as? Position ?: throw IllegalArgumentException("An entity cannot be the target of move commands if it doesn't have a position.")
                event.entity[Position::class.java] = Position.register(pos.entity, x=pos.x, y=pos.y-1)
                System.out.println(event.entity[Position::class.java])
            }
            is MoveDownCommand -> {
                val pos = event.entity[Position::class.java] as? Position ?: throw IllegalArgumentException("An entity cannot be the target of move commands if it doesn't have a position.")
                event.entity[Position::class.java] = Position.register(pos.entity, x=pos.x, y=pos.y+1)
                System.out.println(event.entity[Position::class.java])
            }
            is MoveLeftCommand -> {
                val pos = event.entity[Position::class.java] as? Position ?: throw IllegalArgumentException("An entity cannot be the target of move commands if it doesn't have a position.")
                event.entity[Position::class.java] = Position.register(pos.entity, x=pos.x-1, y=pos.y)
                System.out.println(event.entity[Position::class.java])
            }
            is MoveRightCommand -> {
                val pos = event.entity[Position::class.java] as? Position ?: throw IllegalArgumentException("An entity cannot be the target of move commands if it doesn't have a position.")
                event.entity[Position::class.java] = Position.register(pos.entity, x=pos.x+1, y=pos.y)
                System.out.println(event.entity[Position::class.java])
            }
        }
    }
    override fun reset() {
        running = false
    }
    override fun start() {
        running = true
        getEventQueue("command").members.add(this)
    }
    override fun stop() {
        running = false
    }
    override fun step() {
        if (running) {

        }
    }
}