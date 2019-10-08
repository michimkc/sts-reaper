package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class UndyingPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(UndyingPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "UndyingPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = true;
    //public static final int POWER_AMOUNT = 1;
    // =======================
    public boolean usedPower = false;

    public UndyingPower(final AbstractCreature owner, final AbstractCreature source, int amount) {

        super(owner,owner,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }


    public int onAttacked(DamageInfo info, int damage)
    {
        int totalHP = owner.currentHealth;
        int totalDamage = damage;

        if( damage >= totalHP ) {

            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            AbstractDungeon.effectList.add(new DamageNumberEffect(this.owner, this.owner.hb_x* Settings.scale, this.owner.hb_y*Settings.scale, (int)damage));
            flash();

            damage = totalHP - 1; // reduce the player to 1 hp.
            int negativeDamage = totalDamage - damage; // we still apply marks for damage that would have gone below zero
            // lets say we have 5 hp, but were dealt 10 damage
            // damage = 5 - 1 = 4
            // totalDamage = 10
            // negativeDamage = 10 - 4 = 6
            // so we apply 6 marks.
            if(owner.hasPower(DefaultMod.makeID("VengeancePower")))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, owner,
                        new MarkPower(info.owner, owner, negativeDamage), negativeDamage));
            }
            usedPower = true;

        }
        return damage;
    }

    @Override
    public void atEndOfRound() {
        if(usedPower)
        {

            if (this.amount == 1) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
            }

            usedPower = false;
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UndyingPower(owner, source, amount);
    }
}
