package theReaper.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.DeepCutsAction;
import theReaper.powers.BleedPower;
import theReaper.powers.DeepCutsPower;
import theReaper.powers.FiendFormPower;

public class DeepCuts extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("DeepCuts");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public DeepCuts()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        //damage = baseDamage = 9;
        //damageUp = 3;
        //baseMagicNumber = magicNumber = 1;
        this.exhaust = true;
        magicNumber = baseMagicNumber = 2;
        magicNumberUp = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //act(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
        //                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        //act(new ApplyPowerAction(m, p, new DeepCutsPower(m, p, magicNumber), magicNumber));
        act(new DeepCutsAction(m, this.magicNumber));
    }


}
