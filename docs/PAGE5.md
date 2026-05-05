# Page 5: Event system
The Event System is the heart of FLoader. It allows your mod to listen for specific game actions (like clicking a province, declaring war, or ending a turn) and run your Java code in response.

## 1. How it works?
All events are handled by the EventBus. You can subscribe to an event by providing its unique string name and a Lambda function (Consumer) that will be executed when the event triggers.
Events usually pass data in an Array of Objects (Object[] args). You need to cast these objects to their proper types (like int or String).

## 2. Basic Usage
The best place to subscribe to events is in the onEnable() method of your main class.

```java
@Override
public void onEnable() {
    EventBus.subscribe("onProvinceClick", args -> {
        int provinceID = (int) args[0]; 
        System.out.println("Player clicked on province: " + provinceID);
    });
}
```

## 3. Available Events

| **Event name** | **Arguments** |
| :--- | :--- |
| `onGameInitialization` | None |
| `onTurnEnd` | None |
| `onProvinceClick` | `args[0]` (int): provinceID |
| `onDayChange` | `args[0]` (int): daysPassed |
| `onMonthChange` | `args[0]` (int): monthID |
| `onWarDeclared` | `args[0]` (int): attackerID, `args[1]` (int): defenderID |
| `onProvinceOwnerChanged`| `args[0]` (int): provID, `args[1]` (int): newOwnerID, `args[2]` (boolean): isOccupied |
| `onPeaceSigned` | `args[0]` (int): warID |
| `onGoldChanged` | `args[0]` (int): civID, `args[1]` (long): newAmount |
| `onTechLeveledUp` | `args[0]` (int): civID, `args[1]` (float): newLevel |
| `onIdeologyChanged` | `args[0]` (int): civID, `args[1]` (int): newIdeologyID |
| `onCapitalMoved` | `args[0]` (int): civID, `args[1]` (int): newProvID |
| `onTruceSigned` | `args[0]` (int): civA, `args[1]` (int): civB, `args[2]` (int): turns |
| `onAllianceFormed` | `args[0]` (String): allianceName |
| `onUnionFormed` | `args[0]` (int): civID1, `args[1]` (int): civID2 |
| `onVassalCreated` | `args[0]` (String): vassalTag |
| `onBuildingConstructed` | `args[0]` (int): civID, `args[1]` (int): provID, `args[2]` (String): buildingType |
| `onBuildingDestroyed` | `args[0]` (int): civID, `args[1]` (int): buildingID |
| `onInvestmentStart` | `args[0]` (int): civID, `args[1]` (int): provID, `args[2]` (int): amount, `args[3]` (int): investType |
| `onLoanTaken` | `args[0]` (int): civID, `args[1]` (int): amount |
| `onTreasuryEmpty` | None |
| `onReligionsInit` | None |
| `onIdeologiesInit` | None |

## 4. Important Notes
- **Thread Safety:** Events are triggered in the main game thread. Do not run heavy calculations (like massive file I/O) directly inside an event, as it will freeze the game.
- **Data Types:** Always check the table above for the correct argument index and type. Using the wrong type (e.g., String instead of int) will cause a ClassCastException