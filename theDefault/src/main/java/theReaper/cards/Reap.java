package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.SoulBindAction;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.util.SoulManager;
import theReaper.util.SoulStrings;

import java.util.ArrayList;
import java.util.List;

public class Reap extends AbstractNonLethalCard {

    public static final String ID = DefaultMod.makeID("Reap");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Reap()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 4;
        damageUp = 3;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        act(new SoulBindAction(p,m));

    }

    @Override
    // SOUL BIND TOOLTIP
    public List<TooltipInfo> getCustomTooltips() {
        return SoulManager.getCustomTooltips();
    }
}
