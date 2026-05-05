# Page 6 - Custom content
FLoader allows you to extend the game by adding your own console commands, loading screen elements, and even radio stations. All of this is managed through the registryManager.

## Console commands
ou can register custom commands that can be executed from the in-game console

```java
@Override
public void onPreInitialization() {
    FLoader.registryManager.registerCommand("mycmd", args -> {
        // args[0] - command name
        // args[1], args[N] - command arguments
        if (args.length > 1) {
            System.out.println("Command executed with parameter: " + args[1]);
        } else {
            System.out.println("Hello from FLoader console!");
        }
    });
}
```

## Loading screen
You can customize the player's experience even before the game starts.

### Loading tips
Add new text messages to the pool of random tips shown during startup.

```java
@Override
public void onPreInitialization() {
    FLoader.registryManager.registerLoadingTip("Hello from FLoader");
    FLoader.registryManager.registerLoadingTip("Welcome back :)");
}
```

### Custom loading screens
To add a new loading screen background, you must first load the image and then register its key.

```java
@Override
public void onEnable() {
    EventBus.subscribe("onGameInitialization", args -> {
        FLoader.registryManager.loadImage("myMod", "loading_bg.png");
        FLoader.registryManager.registerLoadingScreen("myMod:loading_bg.png");
    });
}
```

## Custom radio stations
You can create a new radio station with its own set of music tracks.

```java
@Override
public void onPreInitialization() {
    List<String> tracks = new ArrayList<>();
    tracks.add("my_epic_song_1.mp3");
    tracks.add("my_epic_song_2.mp3");

    FLoader.registryManager.registerStation("Modded Radio", tracks);
}
```

## Manual resource registration
If you have an Image object created manually (not from the assets/ folder), you can register it in the global registry to make it accessible to other mods or Mixins.

```java
FLoader.registryManager.registerResource("my_custom_key", myImageObject);
Image img = FLoader.registryManager.getResource("my_custom_key");
```