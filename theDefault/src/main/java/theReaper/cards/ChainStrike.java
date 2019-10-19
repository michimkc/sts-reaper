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
        damageUp = 2;

        this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int attacksPlayedThisTurn = getAttacksPlayedThisTurn(); // dont count this card
        for(int i = 0; i < attacksPlayedThisTurn; i++) {
            act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

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
