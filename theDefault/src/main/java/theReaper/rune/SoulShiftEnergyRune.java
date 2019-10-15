package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class SoulShiftEnergyRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftEnergyRune.class.getName());

    public static String ID = DefaultMod.makeID("SoulShiftEnergyRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public int amount;

    public SoulShiftEnergyRune(int energyCount) {

        this.amount = energyCount;
    }

    public SoulShiftEnergyRune()
    {
        this.amount = 2;
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
        act(new GainEnergyAction(amount));
    }

    public SoulShiftEnergyRune makeCopy()
    {
        return new SoulShiftEnergyRune(this.amount);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
