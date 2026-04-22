# Page 6 - List of all events
This is a **COMPLETE** list of all events currently available in the mod loader

- onGameInitialization: Called after the game has fully loaded and all FLoader systems have been initialized
- onTurnEnd: Called at the end of each turn
- onDayChange(daysPassed): Called when the day changes daysPassed (number) is the number of days that have passed
- onMonthChange(newMonth): Called when the month changes newMonth (number) is the new month's number (1-12)
- onCivDestroyed(civID): A civilization has been destroyed
- onProvinceClick(provinceID): The player clicked on a province - onProvinceOwnerChanged(provinceID, newOwnerCivID, oldOwnerCivID): The province owner has changed
- onCapitalMoved(civID, newCapitalProvID): The civilization has moved its capital
- onIdeologyChanged(civID, newIdeologyID): The civilization has changed its ideology
- onWarDeclared(aggressorCivID, defenderCivID): War has been declared
- onPeaceSigned(peaceTreatyData): A peace treaty has been signed (argument - a complex object, for advanced use)
- onUnionFormed(civA_ID, civB_ID): Two civilizations have formed an alliance
- onAllianceFormed(allianceData): An alliance has been formed onVassalCreated(vassalData): A vassal has been created
- onBuildingConstructed(civID, provinceID, buildingType): A building has been completed in the province
- onBuildingDestroyed(civID, buildingID): The building has been destroyed (eg, by an event)
- onLoanTaken(byCivID, toCivID): A loan has been taken out
- onTreasuryEmpty: Called when the active player runs out of money
- sendNukes(provinceID, byCivID): A nuke has been dropped on the province
- onTechLeveledUp(civID, newTechLevel): The civilization has increased its technology level
- onGoldChanged(civID, newGoldValue): The civilization's gold balance has changed - onTruceSigned(civA_ID, civB_ID, turns): Truce signed