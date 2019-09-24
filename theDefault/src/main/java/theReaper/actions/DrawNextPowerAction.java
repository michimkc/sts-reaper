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

import java.util.ArrayList;

public class DrawNextPowerAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DrawNextPowerAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;

    private AbstractPlayer p;
    public boolean foundPower;
    public static final Logger logger = LogManager.getLogger(DrawNextPowerAction.class.getName());

    // this class draws a random card from the discard pile.
    // for selecting cards from the discard pile, use BetterDiscardPileToHandAction() from the base game

    public DrawNextPowerAction(AbstractPlayer player) {
        this.target = player;
        this.p = player;
        setValues(player, player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        foundPower =false;
    }


    public void update() {
        if (this.duration == 0.5F) {
                ArrayList<AbstractCard> cardGroup = AbstractDungeon.player.drawPile.group;
                if(cardGroup.isEmpty())
                {
                    AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[0], true));
                    foundPower = false;
                    this.isDone = true;
                    return;
                }
                for ( int i = cardGroup.size()-1; i >= 0; i--)
                {
                    AbstractCard c = cardGroup.get(i);
                    if (c.type == AbstractCard.CardType.POWER)
                    {
                        if (this.p.hand.size() == 10) {
                            this.p.drawPile.moveToDiscardPile(c);
                            this.p.createHandIsFullDialog();
                        } else {
                            this.p.drawPile.removeCard(c);
                            AbstractDungeon.player.hand.addToTop(c);
                            AbstractDungeon.player.hand.refreshHandLayout();
                            AbstractDungeon.player.onCardDrawOrDiscard();
                            foundPower = true;
                            this.isDone = true;
                            return;

                        }
                    }

                }
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[1], true));
                foundPower = false;
                this.isDone = true;
        }

        tickDuration();
    }


}
