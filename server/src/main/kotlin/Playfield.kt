package eu.lauwenmark.jxfire.server.playfield

import eu.lauwenmark.jxfire.server.components.Component
import java.math.BigInteger

/**
 * A container for the playfield data.
 *
 * The playfield is nothing more than a 2D grid array. Each cell of that grid is a single square tile in the game.
 * Each of those squares can hold an unbound number of [game entities][eu.lauwenmark.jxfire.server.entities.GameEntity].
 * The playfield is supposedly infinite. It is up to the underlying implementation to decide how to handle this. A
 * possible choice is to return a stub (empty) tile when given a coordinate outside of the stored range. Another is to
 * wrap coordinates around, making some sort of "round" world.
 */
interface Playfield {
    /**
     * Get the content of the playfield cell at the given location.
     *
     * @param x The X position in the cartesian space;
     * @param y The Y position in the cartesian space.
     */
    fun square(x: BigInteger, y: BigInteger): List<MutableSet<Component>>
}

/**
 * A simple implementation of the playfield data.
 *
 * This is a very simple implementation of the [Playfield] interface. The whole playfield is stored in an in-memory
 * two-dimensional array. Any attempt to query a coordinate outside of the storage bounds will generate an exception.
 *
 * @constructor Creates an instance of a playfield with the given width and height.
 */
class TrivialPlayfield(val width: Int, val height: Int) : Playfield {
    val content: Array<Array<List<MutableSet<Component>>>> = Array<Array<List<MutableSet<Component>>>>(width) {
        Array<List<MutableSet<Component>>>(height) {
            ArrayList<MutableSet<Component>>()
        }
    }

    override fun square(x: BigInteger, y: BigInteger): List<MutableSet<Component>> {
        return content[x.intValueExact()][y.intValueExact()]
    }
}

