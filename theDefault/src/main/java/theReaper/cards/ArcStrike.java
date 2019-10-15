package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.ArcStrikeAction;
import theReaper.powers.MarkPower;

public class ArcStrike extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("ArcStrike");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public ArcStrike()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 8;
        baseMagicNumber = magicNumber = 1;
        magicNumberUp = 1;

        this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int totalMarks = 0;
        if (m.hasPower(MarkPower.POWER_ID))
        {
            totalMarks = m.getPower(MarkPower.POWER_ID).amount;
        }

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(totalMarks > 0)
        {
            act(new ArcStrikeAction(m, totalMarks, magicNumber));
        }

    }


}
