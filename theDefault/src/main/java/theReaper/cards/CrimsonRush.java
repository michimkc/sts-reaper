package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;

public class CrimsonRush extends AbstractCustomCard {



    public static final String ID = DefaultMod.makeID("CrimsonRush");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public CrimsonRush()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 7;
        damageUp = 2;
        baseMagicNumber = magicNumber = 7;
        magicNumberUp = 2;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int totalDamage = damage;

        if(m.hasPower(DefaultMod.makeID("BleedPower"))) {
            totalDamage += magicNumber;
            CardCrawlGame.sound.play("BLOOD_SWISH", 0.05F);
        }

        act(new DamageAction(m, new DamageInfo(p, totalDamage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

    }

}
