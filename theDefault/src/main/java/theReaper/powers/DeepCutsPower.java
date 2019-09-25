package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class DeepCutsPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(DeepCutsPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "DeepCutsPower";
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean POWER_ISTURNBASED = true;

    // =======================

    public DeepCutsPower(final AbstractCreature owner, final AbstractCreature source, int amount) {

        super(owner,source,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

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
        return new DeepCutsPower(owner, source, amount);
    }
}
