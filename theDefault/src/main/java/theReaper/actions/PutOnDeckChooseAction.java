package theReaper.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PutOnDeckChooseAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckChooseAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;

    private AbstractPlayer p;
    private boolean isRandom;
    public static int numPlaced;
    public boolean canChoose;
    public static final Logger logger = LogManager.getLogger(SoulBindAction.class.getName());

    public PutOnDeckChooseAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean canChoose) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.isRandom = isRandom;
        this.canChoose = canChoose;
    }


    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.size() < this.amount) {
                this.amount = this.p.hand.size();
            }

            if (this.isRandom) {
                for (int i = 0; i < this.amount; i++) {
                    this.p.hand.moveToDeck(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng), false);
                }
            } else {
                if (this.p.hand.group.size() > this.amount) {
                    numPlaced = this.amount;
                    AbstractDungeon.handCardSelectScreen.open(DESCRIPTIONS[0], this.amount, canChoose, true);
                    tickDuration();
                    return;
                }
                for (int i = 0; i < this.p.hand.size(); i++) {
                    this.p.hand.moveToDeck(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng), this.isRandom);
                }
            }
        }




        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(
                        AbstractDungeon.player.drawPile,AbstractDungeon.handCardSelectScreen.selectedCards, d -> false));
                //this.p.hand.moveToDeck(c, false);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}
