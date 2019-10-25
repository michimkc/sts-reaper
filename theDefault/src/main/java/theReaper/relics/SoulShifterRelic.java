package theReaper.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import java.io.File;

import static theReaper.DefaultMod.makeRelicOutlinePath;
import static theReaper.DefaultMod.makeRelicPath;

public class SoulShifterRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "SoulShifterRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(SoulShifterRelic.class.getName());


    public SoulShifterRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
