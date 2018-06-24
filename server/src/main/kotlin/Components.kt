package eu.lauwenmark.jxfire.server.components

import eu.lauwenmark.jxfire.server.entities.GameEntity

interface Component {
    val entity: GameEntity
}

class Size(override val entity: GameEntity, val width: Int, val height: Int) : Component {

}

class Position(override val entity: GameEntity, val x: Int, val y: Int) : Component {

}

class Caption(override val entity: GameEntity, val caption: String) : Component {

}

class Speed(override val entity: GameEntity, val speed: Int) : Component {

}
