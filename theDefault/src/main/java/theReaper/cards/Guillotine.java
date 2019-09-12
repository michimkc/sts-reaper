package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.SoulBindAction;
import theReaper.actions.WhirlingDeathAction;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.util.SoulManager;
import theReaper.util.SoulStrings;

import java.util.ArrayList;
import java.util.List;

public class Guillotine extends AbstractNonLethalCard {

    public static final String ID = DefaultMod.makeID("Guillotine");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;

    public Guillotine()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 24;
        damageUp = 8;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        NonLethalDamageAction(m,new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SMASH,false);

        act(new SoulBindAction(p,m));

    }

    @Override
    // SOUL BIND TOOLTIP
    public List<TooltipInfo> getCustomTooltips() {
        return SoulManager.getCustomTooltips();
    }

}
