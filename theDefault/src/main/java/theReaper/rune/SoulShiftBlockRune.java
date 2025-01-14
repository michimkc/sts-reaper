package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class SoulShiftBlockRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftBlockRune.class.getName());

    public static String ID = DefaultMod.makeID("SoulShiftBlockRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;
    public static final String imgURL = "theReaperResources/images/ui/greyrunebutton.png";

    public int amount;

    public SoulShiftBlockRune(int amount) {
        super();
        this.amount = amount;
    }

    public SoulShiftBlockRune() {
        super();
        this.amount = 12;
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
        return DESC[0] + this.amount + DESC[1];
    }

    public void onUse()
    {
        act(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player, amount));
    }

    public SoulShiftBlockRune makeCopy()
    {
        return new SoulShiftBlockRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
