package theReaper.rune;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class SoulShiftThornsRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftThornsRune.class.getName());

    public String ID = DefaultMod.makeID("SoulShiftThornsRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public int amount;

    public SoulShiftThornsRune(int amount) {

        this.amount = amount;
    }

    public SoulShiftThornsRune()
    {
        this.amount = 3;
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
        act(new ApplyPowerAction(p,p,new ThornsPower(p,amount),amount));
    }

    public SoulShiftThornsRune makeCopy()
    {
        return new SoulShiftThornsRune(this.amount);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
