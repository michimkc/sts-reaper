package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.powers.DevourPower;


public class DieAction
        extends AbstractGameAction
{

    private static final float DURATION = 0.1F;

    public static final Logger logger = LogManager.getLogger(DieAction.class.getName());

    public AbstractMonster m;

    public DieAction(AbstractMonster m) {


        this.m = m;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }


    public void update() {
        if (this.duration == 0.1F &&
                this.m != null) {


            if(this.m instanceof AwakenedOne && AbstractDungeon.getCurrRoom().cannotLose == true)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m, new DamageInfo(AbstractDungeon.player,this.m.currentHealth, DamageInfo.DamageType.NORMAL),AttackEffect.NONE));
            }
            else {
                this.m.currentHealth = 0;
                this.m.healthBarUpdatedEvent();
                this.m.die();
            }
            this.isDone = true;
        }


        tickDuration();
    }
}
