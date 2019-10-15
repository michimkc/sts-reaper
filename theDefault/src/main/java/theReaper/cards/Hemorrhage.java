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
import theReaper.actions.BleedLoseHpAction;
import theReaper.powers.BleedPower;

import java.util.ArrayList;
import java.util.List;

public class Hemorrhage extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Hemorrhage");

    public static final String MARKSTITLE = BaseMod.getKeywordTitle("thereaper:non-lethal"); // this isn't resolved but should be resolved at compile time when added by BaseMod.
    public static final String MARKSDESCRIPTION = BaseMod.getKeywordDescription("thereaper:non-lethal");

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Hemorrhage() {

        super(ID, COST, TYPE, RARITY, TARGET);
        //bleedNumber = baseBleedNumber = 5;
        //bleedNumberUp = 3;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //act(new ApplyPowerAction(m, p, new BleedPower(m, p, bleedNumber), bleedNumber));
        if(m.hasPower(DefaultMod.makeID("BleedPower")))
        {
            act(new BleedLoseHpAction(m,p,m.getPower(DefaultMod.makeID("BleedPower")).amount, AbstractGameAction.AttackEffect.POISON, false, false));
            //act(new DamageAction(m, new DamageInfo(p, m.getPower(DefaultMod.makeID("BleedPower")).amount, DamageInfo.DamageType.HP_LOSS),
                    //AbstractGameAction.AttackEffect.POISON));
        }
    }

    public void upgrade()
    {
        super.upgrade();
        this.exhaust = false;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();

        this.tips.add(new TooltipInfo(MARKSTITLE,MARKSDESCRIPTION));

        return this.tips;
    }

}