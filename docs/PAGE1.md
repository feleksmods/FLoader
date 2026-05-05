# Page 1: Getting started
Welcome to FLoader Java Edition! This guide will help you set up your development environment and create your first mod.

## 1. Requirements
To develop mods for FLoader, you need:
- **JDK 21+**
- **IDE**: any ide you want, but most of users prefer Intellij IDEA.
- **FLoader API**: Can be found in the libs/ folder of the FLoader distribution.
- **Game JAR**: The original file of Age of History 2: Definitive Edition.

## 2. Project setup
The easiest way to start is to copy the example mod directory from the FLoader repository and use it as a template.
In your pom.xml, make sure to update the paths to your library files:
```xml
<systemPath>${project.basedir}/libs/floaderapi.jar</systemPath>
<!-- and -->
<systemPath>${project.basedir}/libs/game.jar</systemPath>
```

## 3. Entry point
Every FLoader mod must have a main class annotated with @Mod. This class must implement the IMod interface.

```java
package me.myname.mymod;

import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;

@Mod(
    id = "myModID", 
    version = "1.0", 
    author = "YourName", 
    description = "A short description of my mod"
)
public class Main implements IMod {

    @Override
    public void onPreInitialization() {
        // Register commands and assets here
    }

    @Override
    public void onEnable() {
        // Subscribe to events here
    }

    @Override
    public void onDisable() {
        // Cleanup logic (if needed)
    }
}
```

## 5. Building
1. Open your terminal in the mod directory and run: mvn clean package.
2. Find the built .jar file in the target/ folder.
3. Copy your mod .jar file into the fmods/ directory of your game.
4. Run the game using the run.bat file.