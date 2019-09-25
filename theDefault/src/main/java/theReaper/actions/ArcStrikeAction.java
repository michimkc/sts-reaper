package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.powers.MarkPower;


public class ArcStrikeAction
        extends AbstractGameAction
{
    private static final float DURATION = 0.1F;

    public int totalMarks;
    public int multiplier;
    public static final Logger logger = LogManager.getLogger(ArcStrikeAction.class.getName());

    public ArcStrikeAction(AbstractCreature target, int marks, int multiplier) {

        this.target = target;
        this.totalMarks = marks;
        this.multiplier = multiplier;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F &&
                this.target != null && !this.target.isDead) {

            int lastDamageDealt = this.target.lastDamageTaken;

            int damageToDeal = Math.min(lastDamageDealt,totalMarks); // the last damage dealt was done by the attack.

            damageToDeal = damageToDeal * multiplier;

            if(damageToDeal > 0) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, damageToDeal, DamageInfo.DamageType.HP_LOSS),
                        AttackEffect.FIRE));
            }
        }


        tickDuration();
    }
}
