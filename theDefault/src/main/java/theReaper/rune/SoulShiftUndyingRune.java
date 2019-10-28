package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.powers.UndyingPower;
import theReaper.util.ReaperStrings;

public class SoulShiftUndyingRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftUndyingRune.class.getName());

    public static String ID = DefaultMod.makeID("SoulShiftUndyingRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public int amount;

    public SoulShiftUndyingRune(int amount) {

        this.amount = amount;
    }

    public SoulShiftUndyingRune()
    {
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
        return DESC[0] + this.amount + DESC[1];
    }

    public void onUse()
    {
        AbstractCreature p = AbstractDungeon.player;
        act(new ApplyPowerAction(p,p,new UndyingPower(p,p,amount),amount));
    }

    public SoulShiftUndyingRune makeCopy()
    {
        return new SoulShiftUndyingRune(this.amount);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
