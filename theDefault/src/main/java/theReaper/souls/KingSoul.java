package theReaper.souls;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.SoulGemAction;
import theReaper.relics.EggSlicerRelic;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulManager;

public class KingSoul extends AbstractSoul {

    public static final Logger logger = LogManager.getLogger(KingSoul.class.getName());
    // Standard ID/Description
    public static final String soulName = "KingSoul";
    public static final String SOUL_ID = DefaultMod.makeID(soulName);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(SOUL_ID);
    public static final String[] DESC = reaperString.DESCRIPTIONS;
    public static final String imgURL = "theReaperResources/images/souls/KingSoul.png";
    public Texture img;

    public KingSoul()
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
    public void useSoul()
    {

        // what happens when you click the soul

        super.useSoul();
    }

    @Override
    public AbstractSoul makeCopy()
    {

        return new KingSoul();
    }

}
