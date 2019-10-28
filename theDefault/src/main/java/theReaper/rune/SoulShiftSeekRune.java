package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class SoulShiftSeekRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftSeekRune.class.getName());

    public static String ID = DefaultMod.makeID("SoulShiftSeekRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public int amount;

    public SoulShiftSeekRune(int amount) {
        super();
        this.amount = amount;
    }

    public SoulShiftSeekRune() {
        super();
        this.amount = 1;
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

    public void onUse()
    {
        act(new SeekAction(amount));
    }

    public SoulShiftSeekRune makeCopy()
    {
        return new SoulShiftSeekRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
