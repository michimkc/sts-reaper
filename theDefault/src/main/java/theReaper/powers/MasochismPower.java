package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.actions.SetContagionEnabledAction;

public class MasochismPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(MasochismPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "MasochismPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;

    // =======================

    public MasochismPower(final AbstractCreature owner, final AbstractCreature source, int amount) {

        super(owner,source,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
    }

    public void onPowerApplied(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(target.isPlayer && power.type == PowerType.DEBUFF)
        {
            flash();
            logger.info("Debuff applied to player. Drawing " + amount + "card(s).");
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player,this.amount));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if(amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MasochismPower(owner, source, this.amount);
    }
}
