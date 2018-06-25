package eu.lauwenmark.jxfire.server

import eu.lauwenmark.jxfire.server.events.createEventQueue
import eu.lauwenmark.jxfire.server.services.TickService
import eu.lauwenmark.jxfire.server.services.registerService

fun main(args: Array<String>) {
    System.out.println("Hello")
    createEventQueue("main")
    registerService(TickService())

}
