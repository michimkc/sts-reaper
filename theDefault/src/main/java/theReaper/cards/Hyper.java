package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

public class Hyper extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Hyper");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Hyper() {

        super(ID, COST, TYPE, RARITY, TARGET);
        selfBleedNumber = baseSelfBleedNumber = 3;
        magicNumber = baseMagicNumber = 2;
        selfBleedNumberUp = -1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new GainEnergyAction(magicNumber));
        act(new ApplyPowerAction(p, p, new BleedPower(p, p, selfBleedNumber), selfBleedNumber));


    }


}