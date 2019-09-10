package theReaper.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RaiseMaxHpAction extends AbstractGameAction {

    public RaiseMaxHpAction(final AbstractCreature target, final int amount) {
        setValues(target, target, amount);
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.5F;
    }
    
    @Override
    public void update() {

        if (this.duration == 0.5F) {

            target.increaseMaxHp(amount,true);

        }


        tickDuration();

    }
}
