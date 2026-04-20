# Page 5 - fl.event and fl.utils and fl.res
## fl.event
This module allows you to create in-game events.

- showEvent(civID, title, description, imgKey): Shows a simple event for a civilization with a single "OK" button.
- showAdvancedEvent(civID, title, description, imgKey, button1Text, button1Callback, button2Text, button2Callback, ...): Shows an event with multiple buttons, each with its own function.

## fl.utils
This module adds useful utilities.

- log(message): Displays a message in the game console.
- getTurnID(): Returns the current turn number.
- reloadFL(): (Unsafe!) Reloads FLoader and all mods. Useful for quick development, but can lead to instability.
- getMonthName(monthID): Returns the month name by its number.

## fl.res
This module allows you to perform various operations with mod resources.

- loadImage(modName, pathInAssets): Loads an image from your mod's assets folder.

Here's an example with a custom event added:
```lua
local icon = fl.res.loadImage("ExampleMod", "culo.png")

function showEvent()
    local civId = fl.player.getPlayerCiv()
        
    local function onFirst()
        print("You selected 1st option!")
    end
    
    local function onSecond()
        print("You selected 2nd option!")
    end
    
    fl.event.showAdvancedEvent(civId, "example title", "duper cool event description", icon, "Option 1", onFirst, "Option 2", onSecond)
end

fl.registry.registerCommand("event", showEvent)
```