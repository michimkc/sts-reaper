package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.BloodAegisAction;
import theReaper.actions.MoveCardToTopOfDeckAction;

public class BloodAegis extends AbstractCustomCard {


    private static final Logger logger = LogManager.getLogger(BloodAegis.class.getName());

    public static final String ID = DefaultMod.makeID("BloodAegis");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public BloodAegis() {

        super(ID, COST, TYPE, RARITY, TARGET);


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new BloodAegisAction()

        );
    }

}