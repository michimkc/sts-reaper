package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.powers.DevourPower;
import theReaper.powers.MarkPower;


public class ConsumeMarksAction
        extends AbstractGameAction
{
    public int numMarks;
    private static final float DURATION = 0.1F;

    public static final Logger logger = LogManager.getLogger(ConsumeMarksAction.class.getName());

    public ConsumeMarksAction(AbstractCreature target, int amount) {

        this.target = target;
        this.numMarks = amount;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }


    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {

            if(this.target.hasPower(MarkPower.POWER_ID))
            {
                ((MarkPower)this.target.getPower(MarkPower.POWER_ID)).consumeMarks(this.numMarks);
            }
        }


        tickDuration();
    }
}
