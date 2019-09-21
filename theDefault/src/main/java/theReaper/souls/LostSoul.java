package theReaper.souls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class LostSoul extends AbstractSoul {

    public static final Logger logger = LogManager.getLogger(LostSoul.class.getName());
    // Standard ID/Description
    public static final String soulName = "LostSoul";
    public static final String SOUL_ID = DefaultMod.makeID(soulName);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(SOUL_ID);
    public static final String[] DESC = reaperString.DESCRIPTIONS;
    public static final String imgURL = "theReaperResources/images/souls/LostSoul.png";
    public Texture img;

    public LostSoul()
    {
        super(imgURL, SOUL_ID, reaperString.NAME, DESC[0]);
    }

    public String getSoulName()
    {
        return this.soulName;
    }

    @Override
    public void updateDescription(){
        this.description = DESC[0];
    }

    @Override
    public void useSoul() {

        // what happens when you click the soul
        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA")); // play a Jingle Sound.

        act(new DrawCardAction(AbstractDungeon.player, 2));

        super.useSoul();
    }

    @Override
    public AbstractSoul makeCopy()
    {

        return new LostSoul();
    }

}
