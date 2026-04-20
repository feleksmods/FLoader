# Page 3 - World API
This module allows you to retrieve and modify game world data.

Civilization-related functions:
- getCivMoney(civID): Returns the civilization's money supply
- setCivMoney(civID, amount): Sets the civilization's money supply
- getCivName(civID): Returns the civilization's name
- getCivTag(civID): Returns the civilization's tag
- getCapitalId(civID): Returns the capital province's ID
- getCivTechLevel(civID): Returns the technology level
- getDiploPoints(civID): Returns diplomacy points
- setCivIdeology(civID, ideologyID): Sets the civilization's ideology
- declareWar(agressorCivID, defenderCivID): Declares war
- setRelation(civA_ID, civB_ID, value): Sets the relationship from A to B (from -100 to 100)

Province-related functions
- getProvinceOwner(provinceID): Returns the province owner's CivID
- setProvinceOwner(provinceID, newOwnerCivID): Instantly changes the province owner
- getProvinceArmy(provinceID): Returns the number of armies in the province
- getProvinceEconomy(provinceID): Returns the province's economy level
- getProvincePopulation(provinceID): Returns the province's population
- getProvinceStability(provinceID): Returns the province's stability
- getProvinceHappiness(provinceID): Returns the province's happiness
- isCapital(provinceID): Returns true if the province is the capital
- dropNuke(provinceID, byCivID): Drops a nuke on the province on behalf of the civilization

Example code that sets the country '``15'`` to 5000 gold per code:
```lua
function onEvent() 
fl.world.setCivMoney(15, 5000)
end

subscribe("onTurnEnd", onEvent)
```