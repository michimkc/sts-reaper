package theReaper.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class WhirlingDeathAction extends AbstractGameAction {

    public AbstractCard card;

    public WhirlingDeathAction(final AbstractCard card,
                               final int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.amount = amount;
        this.card = card;

    }
    
    @Override
    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;

            return;
        }
        if (this.duration == 0.5F) {

            this.card.baseMagicNumber += this.amount;
        }


        tickDuration();

    }
}
