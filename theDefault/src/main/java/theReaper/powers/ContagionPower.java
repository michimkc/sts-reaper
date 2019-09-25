package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SwirlyBloodEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public boolean enabled;
    // =======================

    public ContagionPower(final AbstractCreature owner, final AbstractCreature source, int damageAmt) {

        super(owner,owner,damageAmt,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        enabled = false;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if(!target.isPlayer && power.type == PowerType.DEBUFF)
        {
            flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPORE_CLOUD_RELEASE"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DarkSmokePuffEffect(target.drawX, target.drawY)));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS), DamageAction.AttackEffect.NONE));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ContagionPower(owner, source, this.amount);
    }
}
