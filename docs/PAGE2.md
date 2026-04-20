# Page 2 - Basic Mod Loader Concepts
1. The fl Global Object
   All functions available below will be available in the fl package. It is organized into modules. Example functions in the API:
- fl.world.declareWar
- fl.player.getPlayerCiv
- fl.res.loadImage

2. The Event System
   This is one of the most important functions of the mod loader – the event system.
   To register an event, write subscribe(eventName, callbackFunction)
- eventName is the name of the event (you can find a detailed list of events in a special file)
- callbackFunction is the function that will be executed when the event occurs
  Code example with an event:
```lua
function onEvent()
    print("on event called!")
end

subscribe("onTurnEnd", onEvent)
```