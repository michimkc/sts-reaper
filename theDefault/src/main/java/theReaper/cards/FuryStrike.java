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
import theReaper.powers.BloodthirstPower;

import java.util.ArrayList;
import java.util.List;

public class FuryStrike extends AbstractCustomCard {

    public static final String MARKSTITLE = BaseMod.getKeywordTitle("thereaper:marks"); // this isn't resolved but should be resolved at compile time when added by BaseMod.
    public static final String MARKSDESCRIPTION = BaseMod.getKeywordDescription("thereaper:marks");
    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    public static final String ID = DefaultMod.makeID("FuryStrike");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    public FuryStrike()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 3;
        damageUp = 1;
        baseMagicNumber = magicNumber = 1;
        magicNumberUp = 1;

        this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        act(new ApplyPowerAction(p, p, new BloodthirstPower(p, p, magicNumber), magicNumber));

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();

        this.tips.add(new TooltipInfo(MARKSTITLE,MARKSDESCRIPTION));

        return this.tips;
    }

}
