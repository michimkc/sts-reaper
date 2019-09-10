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
import theReaper.powers.BleedPower;
import theReaper.powers.BloodthirstPower;

import java.util.ArrayList;
import java.util.List;

public class SerratedKnife extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("SerratedKnife");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public SerratedKnife()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 6;
        damageUp = 2;
        baseMagicNumber = magicNumber = 3;
        magicNumberUp = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        act(new ApplyPowerAction(m, p, new BleedPower(m, p, magicNumber), magicNumber));

    }


}
