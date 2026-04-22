function onGameInit()
    fl.settings.setWindowTitle("Age Of History 2: Definitive Edition - FLoader")
end

fl.registry.registerCommand("reload", function()
    fl.utils.log("Reloading FLoader...")
    fl.utils.reloadFL()
    fl.utils.log("FLoader reloaded!")
end)

subscribe("onGameInitialization", onGameInit)