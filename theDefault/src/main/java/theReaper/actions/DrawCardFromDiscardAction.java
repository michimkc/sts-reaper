package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.cards.AbstractCustomCard;
import theReaper.characters.TheDefault;
import theReaper.powers.AbstractCustomPower;

import static theReaper.DefaultMod.cardsDrawnThisTurn;

public class DrawCardFromDiscardAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DrawCardFromDiscardAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;

    private AbstractPlayer p;
    private boolean isRandom;
    public boolean canChoose;
    public static final Logger logger = LogManager.getLogger(DrawCardFromDiscardAction.class.getName());

    // this class draws a random card from the discard pile.
    // for selecting cards from the discard pile, use BetterDiscardPileToHandAction() from the base game

    public DrawCardFromDiscardAction(AbstractCreature target, int amount) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, target, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
    }


    public void update() {
        if (this.duration == 0.5F) {
                if(AbstractDungeon.player.hand.group.size() == 10)
                {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                    return;
                }

                if(this.p.discardPile.group.size() == 0)
                {
                    // discard pile is empty
                    AbstractPlayer p = AbstractDungeon.player;
                    AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[0], true));
                    this.isDone = true;
                    return;
                } else if ( amount > this.p.discardPile.group.size() )
                {
                    // we are trying to draw more cards than exists in discard pile.. just draw the number in the discard.
                    amount = this.p.discardPile.group.size();
                }

                for (int i = 0; i < this.amount; i++) {
                    if(AbstractDungeon.player.hand.group.size() == 10)
                    {
                        AbstractDungeon.player.createHandIsFullDialog();
                        this.isDone = true;
                        return;
                    }

                    AbstractCard c = this.p.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                    this.p.discardPile.moveToHand(c, this.p.discardPile);
                    notifyOnDrawFromDiscard(c);
                }



        }

        tickDuration();
    }

    public void notifyOnDrawFromDiscard(AbstractCard c)
    {

        c.triggerWhenDrawn();

        for (AbstractPower p : AbstractDungeon.player.powers) {
            p.onCardDraw(c);
        }

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onCardDraw(c);
        }

        AbstractDungeon.player.onCardDrawOrDiscard();

        DefaultMod.cardsDrawnThisTurn++;

        for (AbstractCard card: AbstractDungeon.player.hand.group) {
            if (card instanceof AbstractCustomCard) {
                ((AbstractCustomCard) card).onCardDraw();
            }
        }

    }

}
