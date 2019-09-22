package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.VengeancePower;

import java.util.ArrayList;
import java.util.List;

public class ChainStrike extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("ChainStrike");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public ChainStrike()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 5;
        baseMagicNumber = magicNumber = 3;
        magicNumberUp = 1;

        magicNumber2 = baseMagicNumber2 = damage;

        this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int attacksPlayedThisTurn = getAttacksPlayedThisTurn() - 1; // dont count this card
        int bonus = magicNumber * attacksPlayedThisTurn;
        damage = baseDamage + bonus;
        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

    }

    public void update()
    {
        super.update();

        damage = getCardDamage();
        if(damage != baseDamage) {
            isDamageModified = true;
        }
        initializeDescription();

    }
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        damage = getCardDamage();
        if(damage != baseDamage) {
            isDamageModified = true;
        }
        initializeDescription();

    }

    public int getCardDamage()
    {

        int attacksPlayedThisTurn = getAttacksPlayedThisTurn();
        int bonus = magicNumber * attacksPlayedThisTurn;

        int tempDamage = baseDamage+bonus;


        return tempDamage;
    }
    public int getAttacksPlayedThisTurn()
    {
        int attacksPlayedThisTurn = 0;
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
        {
            if(c.type == CardType.ATTACK)
            {
                attacksPlayedThisTurn++;
            }
        }
        return attacksPlayedThisTurn;
    }


}
