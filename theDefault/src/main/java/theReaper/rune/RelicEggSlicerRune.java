package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.EggSlicerRelic;
import theReaper.util.ReaperStrings;

public class RelicEggSlicerRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicEggSlicerRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicEggSlicerRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;
    public static final String imgURL = "theReaperResources/images/ui/greyrunebutton.png";

    public RelicEggSlicerRune() {
        super(new EggSlicerRelic());
    }

    public String getID()
    {
        return ID;
    }

    public String getName()
    {
        return name;

    }
    public String getDescription()
    {
        return DESC[0];
    }

    public RelicEggSlicerRune makeCopy()
    {
        return new RelicEggSlicerRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
