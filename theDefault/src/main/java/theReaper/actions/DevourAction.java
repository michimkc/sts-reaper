package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.powers.DevourPower;
import theReaper.powers.GluttonyPower;
import theReaper.souls.LostSoul;


public class DevourAction
        extends AbstractGameAction
{
    private int healPercent;
    public boolean applyPower;
    private static final float DURATION = 0.1F;

    public static final Logger logger = LogManager.getLogger(DevourAction.class.getName());

    public DevourAction(AbstractCreature target, int healPercent, boolean applyPower) {

        this.target = target;
        this.healPercent = healPercent;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.applyPower = applyPower;
    }


    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            //this.target.damage(this.info);

            logger.info("Heal percent: " + this.healPercent + ", maxHealth: " + AbstractDungeon.player.maxHealth);
            int totalHeal = MathUtils.floor(AbstractDungeon.player.maxHealth * this.healPercent/100);
            logger.info ("TotalHeal: " + totalHeal);

            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                AbstractDungeon.player.heal(totalHeal);
            } else
            {
                if(applyPower) {
                    AbstractPlayer p = AbstractDungeon.player;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, p, new DevourPower(this.target, p, this.healPercent), 1));
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }


        tickDuration();
    }
}
