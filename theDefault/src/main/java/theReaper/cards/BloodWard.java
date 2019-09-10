package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;
import theReaper.powers.BloodthirstPower;

import java.util.ArrayList;
import java.util.List;

public class BloodWard extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("BloodWard");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public BloodWard() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        baseBlock = block = 4;
        blockUp = 2;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new BleedPower(p, p, magicNumber), magicNumber));
        act(new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));

    }


}