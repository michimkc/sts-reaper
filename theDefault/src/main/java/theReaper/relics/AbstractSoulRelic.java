package theReaper.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import java.io.*;

import static theReaper.DefaultMod.makeRelicOutlinePath;
import static theReaper.DefaultMod.makeRelicPath;

public abstract class AbstractSoulRelic extends CustomRelic {


    // ID, images, text.
    public static String name; // set this.


    public static String ID; // don't set this

    private static final Logger logger = LogManager.getLogger(AbstractSoulRelic.class.getName());

    private static Texture IMG;
    private static Texture OUTLINE;

    public AbstractSoulRelic(String name) {

        super(makeID(name), RelicImgString(ImgType.IMG), RelicImgString(ImgType.OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
        IMG = RelicImgString(ImgType.IMG);
        OUTLINE = RelicImgString(ImgType.OUTLINE);
        ID = makeID(name);
    }

    public static String makeID(String name)
    {
        logger.info("Making Relic: " + DefaultMod.makeID(name));
        return DefaultMod.makeID(name);
    }

    public enum ImgType
    {
        IMG,
        OUTLINE
    }

    public static Texture RelicImgString(ImgType type)
    {

        File f = new File(makeRelicPath(name + "_relic.png"));
        File g = new File(makeRelicOutlinePath(name + "_relic.png"));

        Boolean relicPathExists = false;
        Boolean relicOutlinePathExists = false;
        if(f.exists()) {
            relicPathExists = true;
        } else
        {
            logger.info("Relic Image does not exist. Loading Placeholder.");
        }


        if(g.exists()) {
            relicOutlinePathExists = true;
        } else
        {
            logger.info("Relic Outline Image does not exist. Loading Placeholder.");
        }

        if(relicPathExists && relicOutlinePathExists) {
            if(type == ImgType.IMG) {
                return TextureLoader.getTexture(makeRelicPath(name + "_relic.png"));
            } else
            {
                return TextureLoader.getTexture(makeRelicOutlinePath(name + "_relic.png"));
            }
        } else
        {
            if(type == ImgType.IMG) {
                return TextureLoader.getTexture(makeRelicPath("soul_default_relic.png"));
            } else
            {
                return TextureLoader.getTexture(makeRelicOutlinePath("soul_default_relic.png"));
            }
        }
    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
