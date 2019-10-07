package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.UndyingPower;
import theReaper.powers.VengeancePower;

import java.util.ArrayList;
import java.util.List;

public class LastBreath extends AbstractCustomCard {


    public static final String ID = DefaultMod.makeID("LastBreath");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public LastBreath()
    {

        super(ID, COST, TYPE, RARITY, TARGET);

        baseMagicNumber = magicNumber = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        act(new ApplyPowerAction(p, p, new UndyingPower(p, p, magicNumber), magicNumber));

    }

    public void upgrade()
    {
        super.upgrade();
        this.isInnate = true;
    }

}
