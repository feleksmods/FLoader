package me.felek.floader.lua.fl.res;

import age.of.civilizations2.jakowski.lukasz.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import me.felek.floader.utils.ExitCode;
import me.felek.floader.utils.RegistryManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.io.File;

public class LoadImage extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue modName, LuaValue path) {
        String key = modName.checkjstring() + ":" + path.checkjstring();
        File file = new File("fmods/" + modName.checkjstring() + "/assets/" + path.checkjstring());

        if (file.exists()) {
            try {
                Texture tex = new Texture(Gdx.files.absolute(file.getAbsolutePath()));
                Image img = new Image(tex, Texture.TextureFilter.Linear, Texture.TextureWrap.ClampToEdge);
                RegistryManager.registerResource(key, img);
                return LuaValue.valueOf(key);
            } catch (Exception e) {
                ExitCode.REG_IMAGE_LOAD_FAILED.throwFatalError("LibGDX failed to load texture: " + file.getAbsolutePath());
            }
        }
        return NIL;
    }
}
