package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

public class CrimsonRush extends AbstractCustomCard {



    public static final String ID = DefaultMod.makeID("CrimsonRush");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public CrimsonRush()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 6;
        damageUp = 2;
        magicNumber = baseMagicNumber = 2;
        selfBleedNumber = baseSelfBleedNumber = 4;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int totalDamage = damage;


        act(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        act(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        act(new ApplyPowerAction(p, p, new BleedPower(p, p, selfBleedNumber), selfBleedNumber));

    }

}
