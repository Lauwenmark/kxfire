package eu.lauwenmark.jxfire.server

import eu.lauwenmark.jxfire.server.components.*
import eu.lauwenmark.jxfire.server.controllers.RandomRunnerController
import eu.lauwenmark.jxfire.server.entities.GameEntity
import eu.lauwenmark.jxfire.server.events.createEventQueue
import eu.lauwenmark.jxfire.server.events.getEventQueue
import eu.lauwenmark.jxfire.server.services.*

fun main(args: Array<String>) {
    System.out.println("This is KXFire! The Wonderfully Wonderful RPG!")
    createEventQueue("main")
    createEventQueue("command")
    registerService(TickService())
    registerService(ControllerService())
    registerService(CommandMonitorService())
    registerService(EventMonitorService())

    val rabbit = GameEntity()

    Position(rabbit, x=0, y=0)
    Size(rabbit, width=1, height=1)
    Visible(rabbit, 1)
    Caption(rabbit, "El Rabbit")
    Appearance(rabbit, "rabbit.1")
    Speed(rabbit, 1)
    Sight(rabbit, 1, 4)
    Controlled(rabbit, RandomRunnerController())

    startServices()

    while(true) {
        stepServices()
        getEventQueue("main").flush()
        getEventQueue("command").flush()
        System.out.println("----------------------------------------")
        Thread.sleep(1000)
    }
}
