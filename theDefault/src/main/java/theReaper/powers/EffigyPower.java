package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class EffigyPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(EffigyPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "EffigyPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;


    // =======================

    public EffigyPower(final AbstractCreature owner, final AbstractCreature source, int totalAmount) {

        super(owner,owner,totalAmount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }

    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        flash();
        for(int i = 0; i < amount; i++) {
            AbstractDungeon.player.hand.addToHand(card.makeStatEquivalentCopy());
        }

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

    }



    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if(amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new EffigyPower(owner, source, amount);
    }
}
