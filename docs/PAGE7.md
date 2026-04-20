# Page 7 - fl.steam, fl.settings, fl.keys, fl.api
This chapter describes libraries for working with keys, settings, and Steam.

## fl.steam
This module allows you to perform various functions with Steam.

- grantAchievement(apiName): Grant a Steam achievement. apiName (string) - achievement ID
- setRichPresence(key, value): Set Rich Presence (e.g., "Playing as: Rome")
- getPlaytime(): Returns the time played in minutes
- getSteamName(): Returns the player's Steam nickname
- openSteamUrl(url): Open the URL in the Steam overlay
- openSteamOverlay(dialogName): Open a specific overlay dialog (e.g., "players", "settings")

## fl.api
FLoader API

- getModsList(): Returns a table (array) with the names of all loaded mods
- getModInfo(modName): Returns a table with mod information (name, version, author, etc.)
- getLoaderVersion(): Returns the FLoader version

## fl.settings
Allows you to manage settings Games

Adds get/set for the following values:
```
FogOfWar, SandBoxMode, GuiScale, TotalWarMode, LeadersCanDie, AgeOfChaos, AgeOfChaosTurns, GameWidth, GameHeight, ButtonH, ButtonW, Padding, Density
```

Get/set means you can take any function from above and use it in the format: get/setFuncName
For example: getGameWidth, setGameWidth.

## fl.keys
This is a table containing the numeric codes for all keys. Used with fl.registry.registerKeybind()

Code example:
```lua
function event()
    print("G pressed!")
end

fl.registry.registerBind(fl.keys.G, event)
```