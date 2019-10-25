package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.MoveCardToTopOfDeckAction;
import theReaper.patches.AbstractDungeonScreenPatch;

public class Moonflower extends AbstractCustomCard {


    private static final Logger logger = LogManager.getLogger(Moonflower.class.getName());

    public static final String ID = DefaultMod.makeID("Moonflower");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Moonflower() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        magicNumberUp = 1;

    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        int cardsDrawn = DefaultMod.cardsDrawnThisTurn - AbstractDungeon.player.gameHandSize;

        logger.info("Moonflower triggered. Cards drawn : " + cardsDrawn + " , cardsdrawnthisturn: " + DefaultMod.cardsDrawnThisTurn + ", hand size " + AbstractDungeon.player.gameHandSize);

        if(cardsDrawn > 0)
        {
            // if this card was drawn as one of the first 5 cards then it doesn't count.
            // so if total number of cards drawn is greater than gameHandSize it was drawn after turn start
            flash();
            act(new GainEnergyAction(magicNumber));

            // make the card disappear
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this,AbstractDungeon.player.hand));

        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new MoveCardToTopOfDeckAction(this));
    }

}