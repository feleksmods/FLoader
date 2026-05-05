# Page 2: The mod lifecycle
FLoader manages your mod as a dynamic object. Every mod passes through three main stages during the game's startup and execution.

## 1. Discovery Phase
**When:** Immediately after Java starts, before the game window appears.
1. FLoader scans the fmods/ directory and finds your JAR.
2. It adds your JAR to the System Classpath, making your classes visible to the game.
3. FLoader finds your @Mod class and creates an instance of it.
4. ```onPreInitialization()``` is executed.

**What to do here:**
The graphics engine (LibGDX) is **NOT** yet running.
- Register console commands.
- Register Loading Screen tips.
- **WARNING:** Do NOT use loadImage() or access game managers like CFG.menus here. They haven't been created yet.

## 2. Initialization phase
**When:** When the game engine initializes its internal systems (AA_Game.create).
1. The game sets up OpenGL and loads base configurations.
2. FLoader calls the ```onEnable()``` method.
3. The ```onGameInitialization``` event usually triggers at this time.

**What to do here:**
This is the main stage. The game is fully ready for logic.
1. Subscribe to events via EventBus.
2. Load custom textures and sounds.
3. Modify game world data (gold, provinces, etc.).

## Termination phase
**When:** When the game closes or the mod is manually disabled.
1. FLoader calls the onDisable() method.

**What to do here:**
- Save mod configuration or player data to files.
- Perform cleanup (if necessary).

## Code example

```java
@Mod(id = "example", version = "1.0", author = "Felek", description = "Lifecycle Demo")
public class ExampleMod implements IMod {

    @Override
    public void onPreInitialization() {
        FLoader.registryManager.registerCommand("test", args -> System.out.println("OK"));
    }

    @Override
    public void onEnable() {
        FLoader.registryManager.loadImage("demo", "icon.png");

        EventBus.subscribe("onTurnEnd", args -> {
            System.out.println("Turn ended!");
        });
    }

    @Override
    public void onDisable() {
        System.out.println("Mod disabled. Saving data...");
    }
}
```