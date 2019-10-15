package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public abstract class AbstractSoulShiftRune {


    public static final Logger logger = LogManager.getLogger(AbstractSoulShiftRune.class.getName());

    public static String ID = DefaultMod.makeID("AbstractSoulShiftRune");

    private ReaperStrings reaperString;// = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC;// = reaperString.DESCRIPTIONS;
    public String name;

    public AbstractSoulShiftRune() {


    }

    public abstract String getID();

    public abstract String getName();

    public abstract String getDescription();

    public void onUse()
    {

    }

    public abstract AbstractSoulShiftRune makeCopy();

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
