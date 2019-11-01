package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


import com.megacrit.cardcrawl.cards.DamageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.CrimsonEyesRelic;
import theReaper.relics.ThermometerRelic;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class VengeancePower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(VengeancePower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "VengeancePower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = true;
    //public static final int POWER_AMOUNT = 1;
    // =======================

    public VengeancePower(final AbstractCreature owner, final AbstractCreature source, int amount) {

        super(owner,owner,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

        if (AbstractDungeon.player.hasRelic(DefaultMod.makeID(ThermometerRelic.name))) {
            AbstractDungeon.player.getRelic(DefaultMod.makeID(ThermometerRelic.name)).beginLongPulse();
        }
    }


    public int onAttacked(DamageInfo info, int damageAmount)
    {

        if(info.owner != null && info.owner != owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) // if we were hit by something
        {

                flash();
                MarkPower.applyMarks(info.owner,this.owner,damageAmount);

        }
        return damageAmount;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float totalBonusAmount = 0;

        if (AbstractDungeon.player.hasRelic(DefaultMod.makeID(ThermometerRelic.name))) {

            ThermometerRelic r = (ThermometerRelic)AbstractDungeon.player.getRelic(DefaultMod.makeID(ThermometerRelic.name));


            if (type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS && damage > 0) {

                int numStacks = amount;
                if (numStacks > 0) {
                    totalBonusAmount = Math.max(1, (damage * numStacks * r.bonusPercent / 100));
                }
            }
        }
        return damage + totalBonusAmount;

    }

    public void atStartOfTurn() {

        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void onRemove() // for the Waning feature of Hunters Toll
    {
        super.onRemove();

        AbstractDungeon.player.powers.forEach((p)-> powerOnPowerRemoved(p));

        if (AbstractDungeon.player.hasRelic(DefaultMod.makeID(ThermometerRelic.name))) {
            AbstractDungeon.player.getRelic(DefaultMod.makeID(ThermometerRelic.name)).stopPulse();
        }

        }
    public void powerOnPowerRemoved(AbstractPower p) // for the Waning feature of Hunters Toll
    {
        if(p instanceof AbstractCustomPower)
        {
            ((AbstractCustomPower)p).onPowerRemoved(this);
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VengeancePower(owner, source, amount);
    }
}
