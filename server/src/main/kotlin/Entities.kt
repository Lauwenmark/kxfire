package eu.lauwenmark.jxfire.server.entities

import eu.lauwenmark.jxfire.server.components.Component

/**
 * The base game entity.
 *
 * The game entity is
 */
class GameEntity {
    private val components = HashMap<Class<out Component>, Component>()
    operator fun get(id : Class<out Component>) : Component? = components[id]
    operator fun set(id : Class<out Component>, component : Component) {
        components[id] = component
    }
    fun contains(id: Class<out Component>) = components.containsKey(id)
    override fun toString() : String = "[Entity: ${this.hashCode()}]"

}

val entities = ArrayList<GameEntity>()
