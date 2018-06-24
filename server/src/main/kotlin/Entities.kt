package eu.lauwenmark.jxfire.server.entities

import eu.lauwenmark.jxfire.server.components.Component

class GameEntity {
    val components = HashMap<Class<Component>, Component>()
}
