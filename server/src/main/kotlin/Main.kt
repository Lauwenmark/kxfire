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
    registerService(TickService())
    registerService(ControllerService())
    registerService(CommandMonitorService())
    registerService(EventMonitorService())
    registerService(MoveService())

    //Create a sample game entity, with some basic components.
    val rabbit = GameEntity()
    entities.add(rabbit)
    registerComponent(Position(rabbit, x=0, y=0))
    registerComponent(Size(rabbit, width=1, height=1))
    registerComponent(Visible(rabbit, 1))
    registerComponent(Caption(rabbit, "El Rabbit"))
    registerComponent(Appearance(rabbit, "rabbit.1"))
    registerComponent(Speed(rabbit, 1))
    registerComponent(Sight(rabbit, 1, 4))
    registerComponent(Controlled(rabbit, RandomRunnerController()))

    startServices()

    while(true) {
        stepServices()
        getEventQueue("main").flush()
        getEventQueue("command").flush()
        System.out.println("----------------------------------------")
        Thread.sleep(1000)
    }
}
