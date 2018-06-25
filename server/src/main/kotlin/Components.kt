package eu.lauwenmark.jxfire.server.components

import eu.lauwenmark.jxfire.server.controllers.Controller
import eu.lauwenmark.jxfire.server.entities.GameEntity

abstract class Component(val entity: GameEntity){
    override fun toString() = "[${this.javaClass.simpleName}: ${this.entity}]"
}

val components = HashMap<Class<out Component>, MutableList<Component>>()

fun registerComponent(component: Component) {
    if (components[component.javaClass] == null) components[component.javaClass] = ArrayList()
    components[component.javaClass]?.add(component)
    component.entity[component.javaClass] = component
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
class Size(entity: GameEntity, val width: Int, val height: Int) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, w: $width, h: $height]"
}

/**
 * A Component giving the position of an entity on the playfield.
 */
class Position(entity: GameEntity, val x: Int, val y: Int) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, x: $x, y: $y]"
}

/**
 * A Component holding the name of an entity, as perceived by other entities.
 */
class Caption(entity: GameEntity, val caption: String) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, caption: $caption]"
}

/**
 * A Component describing the speed at which an entity can emit commands.
 */
class Speed(entity: GameEntity, val speed: Int) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, speed:$speed]"
}

/**
 * A Component describing the fact that an entity can be seen.
 * The visibility factor tells how easy it is to spot (the higher it is, the easier).
 */
class Visible(entity: GameEntity, val visibility: Int) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, v: $visibility]"
}

/**
 * A Component describing the ability to perceive visible entities using eyes.
 * The accuracy describes eye sensitivity - the higher it is, the easier it is to
 * see something at a given range.
 * The range describes how far the eye can see.
 */
class Sight(entity: GameEntity, val accuracy: Int, val range: Int) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, range: $range, accuracy: $accuracy]"
}

/**
 * A Component describing the outlook of an entity.
 */
class Appearance(entity: GameEntity, val appearance: String) : Component(entity) {
    override fun toString() = "[${this.javaClass.simpleName}: $entity, appearance: $appearance]"
}

class Controlled(entity: GameEntity, val controller: Controller) : Component(entity) {

    override fun toString() = "[${this.javaClass.simpleName}: $entity, controller: $controller]"
}