package eu.lauwenmark.jxfire.server.components

import eu.lauwenmark.jxfire.server.controllers.Controller
import eu.lauwenmark.jxfire.server.entities.GameEntity

abstract class Component(val entity: GameEntity){
    override fun toString() = "[${this.javaClass.simpleName}: ${this.entity}]"
}

val components = HashMap<Class<out Component>, MutableList<Component>>()

fun registerComponent(component: Component) : Component {
    if (components[component.javaClass] == null) components[component.javaClass] = ArrayList()
    if (component.entity.contains(component.javaClass)) unregisterComponent(component.entity[component.javaClass] ?: throw RuntimeException("This should never happen"))
    components[component.javaClass]?.add(component)
    component.entity[component.javaClass] = component
    return component
}

fun unregisterComponent(component: Component) {
    components[component.javaClass]?.remove(component)
}

fun findComponents(type: Class<out Component>): MutableList<Component> {
    if (components[type] == null) components[type] = ArrayList()
    return components[type] ?: throw RuntimeException("This should never happen.")
}

/**
 * A Component giving the size of an entity on the playfield, in game squares.
 */
class Size private constructor(entity: GameEntity, val width: Int, val height: Int) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, width: Int, height: Int) = registerComponent(Size(entity, width, height)) as Size
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, w: $width, h: $height]"
}

/**
 * A Component giving the position of an entity on the playfield.
 */
class Position private constructor(entity: GameEntity, val x: Int, val y: Int) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, x: Int, y: Int) = registerComponent(Position(entity, x, y)) as Position
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, x: $x, y: $y]"
}

/**
 * A Component holding the name of an entity, as perceived by other entities.
 */
class Caption private constructor(entity: GameEntity, val caption: String) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, caption: String) = registerComponent(Caption(entity, caption)) as Caption
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, caption: $caption]"
}

/**
 * A Component describing the speed at which an entity can emit commands.
 */
class Speed private constructor(entity: GameEntity, val speed: Int) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, speed: Int) = registerComponent(Speed(entity, speed)) as Speed
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, speed:$speed]"
}

/**
 * A Component describing the fact that an entity can be seen.
 * The visibility factor tells how easy it is to spot (the higher it is, the easier).
 */
class Visible private constructor(entity: GameEntity, val visibility: Int) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, visibility: Int) = registerComponent(Visible(entity, visibility)) as Visible
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, v: $visibility]"
}

/**
 * A Component describing the ability to perceive visible entities using eyes.
 * The accuracy describes eye sensitivity - the higher it is, the easier it is to
 * see something at a given range.
 * The range describes how far the eye can see.
 */
class Sight private constructor(entity: GameEntity, val accuracy: Int, val range: Int) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, accuracy: Int, range: Int) = registerComponent(Sight(entity, accuracy, range)) as Sight
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, range: $range, accuracy: $accuracy]"
}

/**
 * A Component describing the outlook of an entity.
 */
class Appearance private constructor(entity: GameEntity, val appearance: String) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, appearance: String) = registerComponent(Appearance(entity, appearance)) as Appearance
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, appearance: $appearance]"
}

class Controlled private constructor(entity: GameEntity, val controller: Controller) : Component(entity) {
    companion object {
        fun register(entity: GameEntity, controller: Controller) = registerComponent(Controlled(entity, controller)) as Controlled
    }
    override fun toString() = "[${this.javaClass.simpleName}: $entity, controller: $controller]"
}