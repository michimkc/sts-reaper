package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class FeralInstinctsPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(FeralInstinctsPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "FeralInstinctsPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;
    public static final float thresholdAmount = 0.25F;

    public boolean isActive;
    // =======================

    public FeralInstinctsPower(final AbstractCreature owner) {

        super(owner,owner,POWER_AMOUNT,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        onChangedHP();

    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType damageType) {

        float finalDamage = damage;
        if (isActive && damage > 0 && damageType == DamageInfo.DamageType.NORMAL) {
            finalDamage += damage*amount;
        }
        return finalDamage;
    }

    public void onChangedHP()
    {
        logger.info("isActive is: " + isActive);
        boolean previousActive = isActive;
        if(meetsHealthThreshold(owner))
        {
            isActive = true;
            logger.info("isActive set to: " + isActive);

        } else
        {
            isActive = false;
            logger.info("isActive set to: " + isActive);

        }
        logger.info("previousActive is : " + previousActive + " and isActive is: " + isActive);
        if(previousActive != isActive)
        {
            logger.info("Flashing");
            flash();
        }
    }


    public boolean meetsHealthThreshold(AbstractCreature p)
    {
        if(p.currentHealth <= p.maxHealth*thresholdAmount)
        {
            return true;
        } else
        {
            return false;
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            int descAmount = amount+1;
            description = DESCRIPTIONS[0] + descAmount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FeralInstinctsPower(owner);
    }
}
