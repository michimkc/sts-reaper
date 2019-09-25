package theReaper.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoveCardToTopOfDeckAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(MoveCardToTopOfDeckAction.class.getName());
    public AbstractCard card;

    public MoveCardToTopOfDeckAction(AbstractCard c) {
        target = AbstractDungeon.player;
        this.target = target;
        this.card = c;
        setValues(target, target, 1);
        this.actionType = ActionType.CARD_MANIPULATION;

    }


    public void update() {
        if (this.duration == 0.5F) {
            for(AbstractGameAction a :AbstractDungeon.actionManager.actions)
            {
                if(a instanceof UseCardAction)
                {
                    if(((UseCardAction) a).reboundCard = true);
                    this.isDone = true;
                    break;

                }
            }
/*
            for(AbstractCard c : AbstractDungeon.player.limbo.group)
            {
                if(c.uuid == card.uuid)
                {
                    logger.info("moving from limbo to deck");
                    AbstractDungeon.actionManager.removeFromQueue(c);
                    AbstractDungeon.player.limbo.moveToDeck(card, false);
                    this.isDone = true;
                    tickDuration();
                    return;
                }
            }

            for(AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if(c.uuid == card.uuid)
                {
                    logger.info("moving from hand to deck");
                    AbstractDungeon.actionManager.removeFromQueue(c);
                    AbstractDungeon.player.hand.moveToDeck(card, false);
                    this.isDone = true;
                    tickDuration();
                    AbstractDungeon.player.hand.refreshHandLayout();
                    return;
                }
            }

            for(AbstractCard c : AbstractDungeon.player.exhaustPile.group)
            {
                if(c.uuid == card.uuid)
                {
                    logger.info("moving from exhaust to deck");
                    AbstractDungeon.actionManager.removeFromQueue(c);
                    AbstractDungeon.player.exhaustPile.moveToDeck(card, false);
                    card.unfadeOut();
                    this.isDone = true;
                    tickDuration();
                    return;
                }
            }

            for(AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if(c.uuid == card.uuid)
                {
                    logger.info("moving from discard to deck");
                    AbstractDungeon.actionManager.removeFromQueue(c);
                    AbstractDungeon.player.discardPile.moveToDeck(card, false);
                    this.isDone = true;
                    tickDuration();
                    return;
                }
            }*/

            logger.info("none");
        }

        tickDuration();
    }
}
