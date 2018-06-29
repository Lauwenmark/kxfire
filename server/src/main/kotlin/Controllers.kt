package eu.lauwenmark.jxfire.server.controllers

import eu.lauwenmark.jxfire.server.components.Component
import eu.lauwenmark.jxfire.server.events.*

interface Controller {
    fun tick(component: Component)
    fun reset()
}

/**
 * A simple implementation of the [eu.lauwenmark.jxfire.server.controllers.Controller] interface.
 *
 * This implementation moves the controlled entity randomly in any of the given four directions, or may also choose to
 * not move at all.
 */
class RandomRunnerController : Controller {
    override fun tick(component: Component) {
        val cmdQueue = getEventQueue("command")
        when (Math.random() * 10) {
            in 0..2 ->
                cmdQueue.post(MoveLeftCommand(component))
            in 2..4 ->
                cmdQueue.post(MoveRightCommand(component))
            in 4..6 ->
                cmdQueue.post(MoveUpCommand(component))
            in 6..8 ->
                cmdQueue.post(MoveDownCommand(component))
            in 8..10 -> {
            }
        //Do nothing.
        }
    }

    override fun reset() {
        //Do nothing (this is a stateless controller)
    }

    override fun toString(): String = "[RandomRunnerController: ${this.hashCode()}"
}