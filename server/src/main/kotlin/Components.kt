package eu.lauwenmark.jxfire.server.components

import eu.lauwenmark.jxfire.server.controllers.Controller
import eu.lauwenmark.jxfire.server.entities.GameEntity

interface Component {
    val entity: GameEntity
}

val components = HashMap<Class<out Component>, MutableList<Component>>()

fun registerComponent(component: Component) {
    if (components[component.javaClass] == null) components[component.javaClass] = ArrayList()
    components[component.javaClass]?.add(component)
}

fun unregisterComponent(component: Component) {
    components[component.javaClass]?.remove(component)
}

fun findComponents(type: Class<out Component>): MutableList<Component> {
    if (components[type] == null) components[type] = ArrayList()
    return components[type] ?: throw RuntimeException("This should never happen.")
}

abstract class AbstractComponent(override val entity: GameEntity) : Component {
    init {
        registerComponent(this)
    }
}

/**
 * A Component giving the size of an entity on the playfield, in game squares.
 */
class Size(override val entity: GameEntity, val width: Int, val height: Int) : AbstractComponent(entity) {

}

/**
 * A Component giving the position of an entity on the playfield.
 */
class Position(override val entity: GameEntity, val x: Int, val y: Int) : AbstractComponent(entity) {

}

/**
 * A Component holding the name of an entity, as perceived by other entities.
 */
class Caption(override val entity: GameEntity, val caption: String) : AbstractComponent(entity) {

}

/**
 * A Component describing the speed at which an entity can emit commands.
 */
class Speed(override val entity: GameEntity, val speed: Int) : AbstractComponent(entity) {

}

/**
 * A Component describing the fact that an entity can be seen.
 * The visibility factor tells how easy it is to spot (the higher it is, the easier).
 */
class Visible(override val entity: GameEntity, val visibility: Int) : AbstractComponent(entity) {

}

/**
 * A Component describing the ability to perceive visible entities using eyes.
 * The accuracy describes eye sensitivity - the higher it is, the easier it is to
 * see something at a given range.
 * The range describes how far the eye can see.
 */
class Sight(override val entity: GameEntity, val accuracy: Int, val range: Int) : AbstractComponent(entity) {

}

/**
 * A Component describing the outlook of an entity.
 */
class Appearance(override val entity: GameEntity, val appearance: String) : AbstractComponent(entity) {

}

class Controlled(override val entity: GameEntity, val controller: Controller) : AbstractComponent(entity) {

}