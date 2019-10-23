package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.DevourAction;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class DevourPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(DevourPower.class.getName());

    public static final String POWER_NAME = "DevourPower";
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean POWER_ISTURNBASED = true;
    public static final int POWER_AMOUNT = 1;

    public int healPercent;

    public DevourPower(final AbstractCreature owner, final AbstractCreature source, int healPercent) {

        super(owner,source,POWER_AMOUNT, POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        this.healPercent = healPercent;
    }

    @Override
    public void onDeath()
    {
        AbstractDungeon.actionManager.addToBottom(new DevourAction(owner, this.healPercent, false));
    }

    public void atEndOfRound() {

        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DevourPower(owner, source, this.healPercent);
    }
}
