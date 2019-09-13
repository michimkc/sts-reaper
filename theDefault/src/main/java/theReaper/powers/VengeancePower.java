package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


import com.megacrit.cardcrawl.cards.DamageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makePowerPath;

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

    }


    public int onAttacked(DamageInfo info, int damageAmount)
    {

        if(info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) // if we were hit by something
        {

                flash();
                // Add marks to the enemy.
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, owner,
                    new MarkPower(info.owner, owner, damageAmount), damageAmount));
                AbstractDungeon.player.powers.forEach((p)-> powerOnApplyMarks(p,info.owner, owner,damageAmount));

        }
        return damageAmount;
    }

    public void powerOnApplyMarks(AbstractPower p, AbstractCreature target, AbstractCreature source, int amount)
    {
        if(p instanceof AbstractCustomPower)
        {
            ((AbstractCustomPower)p).onApplyMarks(target, source,amount);
        }
    }

    public void atStartOfTurn() {

        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void onRemove()
    {
        super.onRemove();

        AbstractDungeon.player.powers.forEach((p)-> powerOnPowerRemoved(p));

    }
    public void powerOnPowerRemoved(AbstractPower p)
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
