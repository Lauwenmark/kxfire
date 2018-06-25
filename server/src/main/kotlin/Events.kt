package eu.lauwenmark.jxfire.server.events

import eu.lauwenmark.jxfire.server.components.Component
import java.util.concurrent.ConcurrentLinkedQueue

interface Event

abstract class AbstractComponentEvent(val component: Component) : Event {
    val entity = component.entity
    override fun toString(): String = "[${this.javaClass.simpleName}: ${this.component}]"
}
class TickEvent(val count: Long) : Event
class MoveLeftCommand(component: Component) : AbstractComponentEvent(component)
class MoveRightCommand(component: Component) : AbstractComponentEvent(component)
class MoveUpCommand(component: Component) : AbstractComponentEvent(component)
class MoveDownCommand(component: Component) : AbstractComponentEvent(component)

interface EventListener {
    fun eventReceived(event: Event)
}

class EventQueue {
    val members = ArrayList<EventListener>()
    private val queue = ConcurrentLinkedQueue<Event>()

    fun post(event: Event) {
        queue.add(event)
    }

    fun flush() {
        while (queue.isNotEmpty()) {
            val event = queue.remove()
            for(listener in members) {
                listener.eventReceived(event)
            }
        }
    }
}

private val eventQueues = HashMap<String, EventQueue>()

fun createEventQueue(name: String) {
    if (eventQueues.containsKey(name))
        throw IllegalArgumentException("An event queue of that name already exists.")
    else
        eventQueues[name] = EventQueue()
}

fun getEventQueue(name: String) : EventQueue {
    return eventQueues[name] ?: throw IllegalArgumentException()
}
