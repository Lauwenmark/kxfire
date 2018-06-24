package eu.lauwenmark.jxfire.server

import eu.lauwenmark.jxfire.server.entities.GameEntity
import java.math.BigInteger

fun main(args: Array<String>) {
    System.out.println("Hello");
}

interface Playfield {
    fun square(x: BigInteger, y: BigInteger) : List<GameEntity>
}

class TrivialPlayfield(val width: Int, val height: Int) : Playfield {
    val content : Array<Array<List<GameEntity>>> = Array<Array<List<GameEntity>>>(width) {
        Array<List<GameEntity>>(height) {
            ArrayList<GameEntity>()
        }
    }
    override fun square(x: BigInteger, y: BigInteger): List<GameEntity> {
        return content[x.intValueExact()][y.intValueExact()]
    }
}

