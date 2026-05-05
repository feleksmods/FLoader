# FLoader Build Instructions

## 1. Necessary Tools
To build FLoader, you'll need several tools:
- JDK 21+
- Git (optional)
- A copy of Age of History 2: Definitive Edition

## 2. Building
1. Clone/download the FLoader source code and place it in an empty folder.
2. Copy the original `game.jar` file into the `libs/` folder in your project directory.
3. Open a command prompt and enter:
   ```bash
   ./gradlew clean shadowJar
   ```
   This command will download all dependencies, build the API and the main loader.

## 3. Installation
1. Go to the `build/libs/` folder.
2. Copy the file **`FLoader.jar`** (NOT .java) into your game's root folder.
3. (Optional) Copy `libs/floaderapi.jar` into your own mod project to use the API.
4. Ensure there is an `fmods/` directory in your game folder.

## 4. Running the game
**Note:** You must use Java 21 to run the game with FLoader.

1. **Windows (PowerShell)**
```powershell
& java -javaagent:"FLoader.jar" -jar game.jar
```

2. **Linux (WSL/Terminal)**
```shell
java -javaagent:FLoader.jar -jar game.jar
```

## 5. For modders
1. You can use the `example` module as a template for your mod.
2. Set `game.jar` and `floaderapi.jar` as **provided/system** dependencies in your build tool (Maven/Gradle).
3. Put your resources (images, sounds, etc.) in the **`src/main/resources/assets/`** directory.
4. Place the built mod `.jar` into the `fmods/` directory.