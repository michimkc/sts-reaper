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
import theReaper.actions.SoulBindAction;
import theReaper.powers.BloodthirstPower;
import theReaper.util.SoulManager;

import java.util.ArrayList;
import java.util.List;

public class MasterBall extends AbstractCustomCard {


    public static final String ID = DefaultMod.makeID("MasterBall");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public MasterBall()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 99;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new SoulBindAction(p,m,this.magicNumber));

    }

    @Override
    // SOUL BIND TOOLTIP
    public List<TooltipInfo> getCustomTooltips() {
        return SoulManager.getCustomTooltips(this.magicNumber);
    }

}
