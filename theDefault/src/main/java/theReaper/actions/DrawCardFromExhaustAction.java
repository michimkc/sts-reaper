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
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.cards.AbstractCustomCard;
import theReaper.cards.Invigorate;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawCardFromExhaustAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DrawCardFromExhaustAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;

    private AbstractPlayer p;
    private boolean isRandom;
    public boolean canChoose;
    public static final Logger logger = LogManager.getLogger(DrawCardFromExhaustAction.class.getName());

    // this class draws a random card from the discard pile.
    // for selecting cards from the discard pile, use BetterDiscardPileToHandAction() from the base game

    public DrawCardFromExhaustAction(AbstractCreature target, int amount) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, target, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
    }


    public void update() {
        if (this.duration == 0.5F) {
            if(this.p.exhaustPile.group.size() == 0)
            {
                // exhaust pile is empty
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[0], true));
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> invigoratesRemoved = new ArrayList();

            for(Iterator<AbstractCard> it = this.p.exhaustPile.group.iterator(); it.hasNext();)
            {
                AbstractCard card = it.next();
                if(card instanceof Invigorate)
                {
                    invigoratesRemoved.add(card);
                    it.remove();
                    //this.p.exhaustPile.group.remove(card);
                }
            }

            if(this.p.exhaustPile.group.size() == 0)
            {
                // exhaust pile is empty without invigorates
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[0], true));
                this.isDone = true;

                this.p.exhaustPile.group.addAll(invigoratesRemoved);
                invigoratesRemoved.clear();
                return;
            }

            if ( amount > this.p.exhaustPile.group.size() )
            {
                // we are trying to draw more cards than exists in exhaust pile.. just draw the number in the exhaust.
                amount = this.p.exhaustPile.group.size();
            }

            boolean handFull = false;
            for (int i = 0; i < this.amount; i++) {
                if(AbstractDungeon.player.hand.group.size() >= 10)
                {
                    if(!handFull) {
                        AbstractDungeon.player.createHandIsFullDialog();
                        handFull = true;
                    }
                    AbstractCard c = this.p.exhaustPile.getRandomCard(AbstractDungeon.cardRandomRng);
                    this.p.exhaustPile.moveToDiscardPile(c);
                } else {
                    AbstractCard c = this.p.exhaustPile.getRandomCard(AbstractDungeon.cardRandomRng);
                    this.p.hand.addToHand(c);
                    this.p.exhaustPile.removeCard(c);
                    this.p.hand.refreshHandLayout();
                    c.unhover();
                    c.unfadeOut();
                    notifyOnDrawFromExhaust(c);
                }
            }

            this.p.exhaustPile.group.addAll(invigoratesRemoved);
            invigoratesRemoved.clear();



        }

        tickDuration();
    }

    public void notifyOnDrawFromExhaust(AbstractCard c)
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
