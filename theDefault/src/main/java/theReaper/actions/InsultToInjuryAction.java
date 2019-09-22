package theReaper.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.powers.AbstractCustomPower;

public class InsultToInjuryAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("InsultToInjuryAction");
    private static final String[] TEXT = uiStrings.TEXT;

    private static final UIStrings dissStrings = CardCrawlGame.languagePack.getUIString("InsultToInjuryDisses");
    private static final String[] DISSES = dissStrings.TEXT;

    private AbstractPlayer p;
    public AbstractCard card;

    public static final Logger logger = LogManager.getLogger(InsultToInjuryAction.class.getName());

    // this class draws a random card from the discard pile.
    // for selecting cards from the discard pile, use BetterDiscardPileToHandAction() from the base game

    public InsultToInjuryAction(AbstractPlayer p, AbstractCreature target, int amount, AbstractCard c) {
        this.p = p;
        setValues(target, p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        card = c;
    }


    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));

            tickDuration();

            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {


            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                int count = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
                for(int i = 0; i < count; i++) {

                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY)));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, amount, DamageInfo.DamageType.NORMAL), AttackEffect.NONE));
                }


                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                    c.triggerOnManualDiscard();
                }
                int dissIndex = MathUtils.random(0, TEXT.length-1);
                AbstractDungeon.effectList.add(new SpeechBubble(card.current_x, card.current_y+ (350.0F * Settings.scale), 1.5F, DISSES[dissIndex], true));
            }


            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

        }

        tickDuration();
    }

}
