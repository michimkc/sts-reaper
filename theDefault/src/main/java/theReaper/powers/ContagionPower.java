package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SwirlyBloodEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.actions.SetContagionEnabledAction;
import theReaper.souls.AbstractSoul;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class ContagionPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(ContagionPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "ContagionPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;

    public boolean enabled = true;
    // =======================

    public ContagionPower(final AbstractCreature owner, final AbstractCreature source, int damageAmt) {

        super(owner,source,damageAmt,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        this.enabled = true;
    }

    public void onPowerApplied(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        logger.info("Trigger Contagion");
        if(!target.isPlayer && power.type == PowerType.DEBUFF && enabled)
        {
            flash();
            logger.info("Debuff applied to this enemy.");
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPORE_CLOUD_RELEASE"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DarkSmokePuffEffect(target.drawX, target.drawY)));

            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
            {

                logger.info("Looping through monsters. Monster is: " + m.name + " , ID: " + m.id);
                AbstractDungeon.actionManager.addToBottom(new SetContagionEnabledAction(m,false));
                if(m != target) {
                    logger.info("Applying power: " + power.name + " , amount: " + power.amount);
                    AbstractPower newPower = createNewPower(m, source, power, power.amount);
                    if(newPower != null) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, source, newPower, power.amount));
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new SetContagionEnabledAction(m,true));
            }

        }
    }

    public AbstractPower createNewPower(AbstractCreature target, AbstractCreature source, AbstractPower power, int amount)
    {
        AbstractPower temp = null;
        if(power instanceof ContagionPower)
        {
          // dont copy contagion
        } else if(power instanceof AbstractCustomPower)
        {
            temp = ((AbstractCustomPower)power).makeCopy();
            temp.owner = target;
            temp.amount = amount;

        } else if (power instanceof BleedPower)
        {
            temp = new BleedPower(target,source,amount);
        } else if (power instanceof MarkPower)
        {
            temp = new MarkPower(target,source,amount);
        }
        else if (power instanceof WeakPower)
        {
            temp = new WeakPower(target,amount,!source.isPlayer);

        } else if (power instanceof FrailPower)
        {
            temp = new FrailPower(target, amount, !source.isPlayer);

        } else if (power instanceof VulnerablePower)
        {
            temp = new VulnerablePower(target, amount, !source.isPlayer);

        } else if (power instanceof SlowPower)
        {
            temp = new SlowPower(target, amount);
        } else if (power instanceof LockOnPower)
        {
            temp = new LockOnPower(target, amount);
        } else if (power instanceof AttackBurnPower)
        {
            temp = new AttackBurnPower(target, amount);
        } else if (power instanceof ChokePower)
        {
            temp = new ChokePower(target, amount);
        } else if (power instanceof ConstrictedPower)
        {
            temp = new ConstrictedPower(target,source,amount);
        } else if (power instanceof CorpseExplosionPower)
        {
            temp = new CorpseExplosionPower(target);
        } else if (power instanceof EntanglePower)
        {
            // this doesnt apply to monsters either
        } else if (power instanceof LoseDexterityPower)
        {
            temp = new LoseDexterityPower(target, amount);
        } else if (power instanceof LoseStrengthPower)
        {
            temp = new LoseStrengthPower(target, amount);
        } else if (power instanceof PoisonPower)
        {
            temp = new PoisonPower(target,source,amount);
        }
        else {
            logger.info("Don't have a way to copy power: " + power.name);
        }
        return temp;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ContagionPower(owner, source, this.amount);
    }
}
