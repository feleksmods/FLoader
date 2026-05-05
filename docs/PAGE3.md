# Page 3: Assets & Resources
FLoader features a robust asset management system. You can easily bundle textures, sounds, and other files inside your mod's JAR and even override original game files without touching the game.jar.

## Folder structure
All your resources must be placed in the src/main/resources/assets/ directory of your project. When FLoader searches for a file, it looks inside this folder in all loaded mods.

**Example structure**:
```
MyMod.jar
└── assets/
    ├── UI/
    │   └── custom_button.png
    ├── gfx/
    │   └── flags/
    │       └── POL.png   <-- This will override the vanilla Polish flag!
    └── sounds/
        └── my_music.mp3
```

## Loading Custom Images via API
To use new images (for events, buttons, etc.) that aren't in the base game, you need to load them into memory using the registryManager.
You cannot load images during onPreInitialization. You must wait until the game's graphics engine is ready. The best place is inside the onGameInitialization event.

```java
@Override
public void onEnable() {
    EventBus.subscribe("onGameInitialization", args -> {
        //Load image from: src/main/resources/assets/icons/star.png
        myCustomImage = FLoader.registryManager.loadImage("myMod", "icons/star.png");
        
        if (myCustomImage != null) {
            System.out.println("Texture loaded successfully!");
        }
    });
}
```

## Accessing Resources
Once a resource is loaded, any mod can access it using its unique key:

```java
Image img = FLoader.registryManager.getResource("myMod:icons/star.png");
```