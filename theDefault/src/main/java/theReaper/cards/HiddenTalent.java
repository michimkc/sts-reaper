package theReaper.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.DrawNextPowerAction;
import theReaper.actions.FeverDreamAction;

public class HiddenTalent extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("HiddenTalent");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public HiddenTalent() {

        super(ID, COST, TYPE, RARITY, TARGET);

        magicNumber = baseMagicNumber = 1;
        magicNumberUp = 1;
        this.isInnate = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; i++) {
            act(new DrawNextPowerAction(p));
        }


    }


}