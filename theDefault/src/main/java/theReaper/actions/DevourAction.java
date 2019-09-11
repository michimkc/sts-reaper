package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.LostSoul;


public class DevourAction
        extends AbstractGameAction
{
    private int healPercent;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public static final Logger logger = LogManager.getLogger(DevourAction.class.getName());

    public DevourAction(AbstractCreature target, DamageInfo info, int healPercent) {
        this.info = info;
        setValues(target, info);
        this.healPercent = healPercent;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }


    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);

            logger.info("Heal percent: " + this.healPercent + ", maxHealth: " + AbstractDungeon.player.maxHealth);
            int totalHeal = MathUtils.floor(AbstractDungeon.player.maxHealth * this.healPercent/100);
            logger.info ("TotalHeal: " + totalHeal);

            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                AbstractDungeon.player.heal(totalHeal);
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }


        tickDuration();
    }
}
