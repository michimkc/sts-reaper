package theReaper.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.AegisAction;

public class Aegis extends AbstractCustomCard {


    private static final Logger logger = LogManager.getLogger(Aegis.class.getName());

    public static final String ID = DefaultMod.makeID("Aegis");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Aegis() {

        super(ID, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new AegisAction()

        );
    }

    public void upgrade()
    {
        super.upgrade();
        this.exhaust = false;
    }

}