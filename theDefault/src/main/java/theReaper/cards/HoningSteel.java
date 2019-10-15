package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.ArcStrikeAction;
import theReaper.powers.MarkPower;
import theReaper.powers.VengeancePower;

public class HoningSteel extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("HoningSteel");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    public HoningSteel()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 0;
        newCost = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if(damage > 15)
        {
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        } else if (damage > 30)
        {
            effect = AbstractGameAction.AttackEffect.SMASH;
        }
        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));

    }

    public void calculateCardDamage(AbstractMonster m) {
        damage = baseDamage = DefaultMod.totalMarksConsumedThisCombat;
        super.calculateCardDamage(m);

        isDamageModified = true;

    }

    @Override
    public void onCardDraw() {
        damage = baseDamage = DefaultMod.totalMarksConsumedThisCombat;
        isDamageModified = true;
        initializeDescription();
    }


    public void onConsumeMarks(int numConsumed)
    {
        damage = baseDamage = DefaultMod.totalMarksConsumedThisCombat;
        isDamageModified = true;
        initializeDescription();
    }



}
