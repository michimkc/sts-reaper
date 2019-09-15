package theReaper.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.FiendFormPower;

public class FiendForm extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("FiendForm");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;

    public FiendForm()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        newCost = 2;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new FiendFormPower(p, p), magicNumber));

        this.tags.add(BaseModCardTags.FORM);
    }

    public void upgrade(){
        super.upgrade();
    }

}
