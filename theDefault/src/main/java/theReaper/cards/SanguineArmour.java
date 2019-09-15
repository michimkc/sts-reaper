package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

public class SanguineArmour extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("SanguineArmour");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public SanguineArmour() {

        super(ID, COST, TYPE, RARITY, TARGET);
        selfBleedNumber = baseSelfBleedNumber = 2;
        baseBlock = block = 6;
        blockUp = 3;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new BleedPower(p, p, selfBleedNumber), selfBleedNumber));
        act(new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));

    }


}