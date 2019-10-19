package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.MoveCardToTopOfDeckAction;

public class CrystalRose extends AbstractCustomCard {


    private static final Logger logger = LogManager.getLogger(CrystalRose.class.getName());

    public static final String ID = DefaultMod.makeID("CrystalRose");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    public CrystalRose() {

        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = 5;
        blockUp = 3;
        this.isEthereal = true;

    }

    @Override
    public void triggerWhenDrawn() {

        AbstractPlayer p = AbstractDungeon.player;
        act(new GainBlockAction(p,p,block));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new MoveCardToTopOfDeckAction(this));
    }

}