package eu.lauwenmark.jxfire.server

import eu.lauwenmark.jxfire.server.components.*
import eu.lauwenmark.jxfire.server.controllers.RandomRunnerController
import eu.lauwenmark.jxfire.server.entities.GameEntity
import eu.lauwenmark.jxfire.server.entities.entities
import eu.lauwenmark.jxfire.server.events.createEventQueue
import eu.lauwenmark.jxfire.server.events.getEventQueue
import eu.lauwenmark.jxfire.server.services.*

fun main(args: Array<String>) {
    System.out.println("This is KXFire! The Wonderfully Wonderful RPG!")
    //Create the events queue. "main" is for events, "command" for commands emitted by entities through services.
    createEventQueue("main")
    createEventQueue("command")
    //Register game services
    TickService.register()
    ControllerService.register()
    CommandMonitorService.register()
    EventMonitorService.register()
    MoveService.register()

    //Create a sample game entity, with some basic components.
    val rabbit = GameEntity()
    entities.add(rabbit)
    Position.register(rabbit, x=0, y=0)
    Size.register(rabbit, width=1, height=1)
    Visible.register(rabbit, 1)
    Caption.register(rabbit, "El Rabbit")
    Appearance.register(rabbit, "rabbit.1")
    Speed.register(rabbit, 1)
    Sight.register(rabbit, 1, 4)
    Controlled.register(rabbit, RandomRunnerController())

    startServices()

    while(true) {
        stepServices()
        getEventQueue("main").flush()
        getEventQueue("command").flush()
        System.out.println("----------------------------------------")
        Thread.sleep(1000)
    }
}
