package eu.lauwenmark.jxfire.server.components

import eu.lauwenmark.jxfire.server.entities.GameEntity

interface Component {
    val entity: GameEntity
}

val components = HashMap<Class<Component>, MutableList<Component>>()

fun registerComponent(component: Component) {
    if (components[component.javaClass] == null) components[component.javaClass] = ArrayList()
    components[component.javaClass]?.add(component)
}

fun unregisterComponent(component: Component) {
    components[component.javaClass]?.remove(component)
}

/**
 * A Component giving the size of an entity on the playfield, in game squares.
 */
class Size(override val entity: GameEntity, val width: Int, val height: Int) : Component {

}

/**
 * A Component giving the position of an entity on the playfield.
 */
class Position(override val entity: GameEntity, val x: Int, val y: Int) : Component {

}

/**
 * A Component holding the name of an entity, as perceived by other entities.
 */
class Caption(override val entity: GameEntity, val caption: String) : Component {

}

/**
 * A Component describing the speed at which an entity can emit commands.
 */
class Speed(override val entity: GameEntity, val speed: Int) : Component {

}

/**
 * A Component describing the fact that an entity can be seen.
 * The visibility factor tells how easy it is to spot (the higher it is, the easier).
 */
class Visible(override val entity: GameEntity, val visibility: Int) : Component {

}

/**
 * A Component describing the ability to perceive visible entities using eyes.
 * The accuracy describes eye sensitivity - the higher it is, the easier it is to
 * see something at a given range.
 * The range describes how far the eye can see.
 */
class Sight(override val entity: GameEntity, val accuracy: Int, val range: Int) : Component {

}

/**
 * A Component describing the outlook of an entity.
 */
class Appearance(override val entity: GameEntity, val appearance: String) : Component {

}