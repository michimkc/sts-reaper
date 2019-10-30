package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class LaceratePower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(LaceratePower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "LaceratePower";
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean POWER_ISTURNBASED = true;
    // =======================

    public LaceratePower(final AbstractCreature owner, final AbstractCreature source, int POWER_AMOUNT) {

        super(owner,source,POWER_AMOUNT, POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new BleedPower(owner, source, amount), amount));
        }
        return damageAmount;
    }

    public void atEndOfRound() {

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

    }
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LaceratePower(owner, source, amount);
    }
}
