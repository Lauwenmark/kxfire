package eu.lauwenmark.jxfire.server.entities

import eu.lauwenmark.jxfire.server.components.Component

class GameEntity {
    private val components = HashMap<Class<out Component>, Component>()
    operator fun get(id : Class<out Component>) : Component? = components[id]
    operator fun set(id : Class<out Component>, component : Component) {
        components[id] = component
    }

    override fun toString() : String = "[Entity: ${this.hashCode()}]"

}

val entities = ArrayList<GameEntity>()
