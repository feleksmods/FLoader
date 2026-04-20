# Page 1 - Creating the Basic Mod Structure
Before creating scripts and adding anything new to AoH, you need to create the mod structure.

1. Create a folder structure
Create a folder structure like this
```
fmods/
   ExampleMod/
      assets/
      config/
         config.json
      scripts/
         main.lua
```

2. Configuration file (config.json)
This is the heart of your mod. Here you should enter general information about your mod.

fmods/ExampleMod/config/config.json
```json
{
  "name": "ExampleMod",
  "version": "1.0.0",
  "description": "Cool modification for FLoader",
  "author": "FelekDevYT",
  "mainFile": "main.lua",
  "dependencies": []
}
```

name - the name of your mod; must match the mod's folder.
version - your mod's version
description - your mod's description
author - the mod's author
mainFile - your mod's main script file
dependencies - a list of other mods required for your mod to function

Launch the game. If you filled out everything correctly, you'll see a message in the console.