# FLoader Build Instructions

## 1. Necessary Tools
To build FLoader, you'll need several tools:
- JDK 11+
- Git (optional)
- A copy of Age of History 2: Definitive Edition

## 2. Building and Running
1. Clone/download the FLoader source code and place it in an empty folder.
2. Copy the original game.jar file to the libs/ folder in your project folder.
3. Open a command prompt and enter ```./gradlew shadowJar```. This command will download all dependencies and build a jar file.
4. Copy the finished file (build/libs/FLoader.jar) and place it in the game folder.
5. In the game folder, run ```java -javaagent:FLoader.jar -cp "FLoader.jar;game.jar" aoc.kingdoms.lukasz.jakowski.desktop.DesktopLauncher```