package theReaper.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.DrawCardFromDiscardAction;
import theReaper.actions.DrawCardFromExhaustAction;

public class Invigorate extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Invigorate");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Invigorate() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        magicNumberUp = 1;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

         act(new DrawCardFromExhaustAction(p,magicNumber)); // draw cards
    }


}