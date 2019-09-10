package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.WhirlingDeathAction;

import java.util.ArrayList;

public class WhirlingDeath extends AbstractCustomCard {

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    public static final String ID = DefaultMod.makeID("WhirlingDeath");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    public WhirlingDeath()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 4;
        magicNumber = baseMagicNumber = 3;
        damageUp = 1;

        magicNumber2 = baseMagicNumber2 = 1; // how much to increase the # of hits.

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for(int i = 0; i < magicNumber; i++) {
            act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        act(new WhirlingDeathAction(this,this.magicNumber2));
    }

}
