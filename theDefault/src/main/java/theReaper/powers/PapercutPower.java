package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class PapercutPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(PapercutPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "PapercutPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;

    public int damageAmount;
    public boolean enabled;
    // =======================

    public PapercutPower(final AbstractCreature owner, final AbstractCreature source, int damageAmt) {

        super(owner,owner,damageAmt,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        damageAmount = damageAmt;
        enabled = false;
    }


    public void onCardDraw(AbstractCard c) {
        if (enabled) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.2F));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner,
                    DamageInfo.createDamageMatrix(this.amount, true),
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        enabled = true;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        enabled = false;
    }

    public void onUseSoul(AbstractSoul s)
    {
        enabled = true;
    }

    public void onAfterUseSoul(AbstractSoul s)
    {
        enabled = false;
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PapercutPower(owner, source, this.amount);
    }
}
