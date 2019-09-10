package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.VengeancePower;

import java.util.ArrayList;
import java.util.List;

public class Frenzy extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Frenzy");

    public static final String MARKSTITLE = BaseMod.getKeywordTitle("thereaper:marks"); // this isn't resolved but should be resolved at compile time when added by BaseMod.
    public static final String MARKSDESCRIPTION = BaseMod.getKeywordDescription("thereaper:marks");

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Frenzy() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        magicNumber2 = baseMagicNumber2 = 2;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DrawCardAction(p,magicNumber)); // draw cards
        act(new ApplyPowerAction(p, p, new VengeancePower(p, p, magicNumber2), magicNumber2));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.exhaust = false;
            upgradeName();
            upgradeDamage(damageUp);
            upgradeBlock(blockUp);
            upgradeMagicNumber(magicNumberUp);
            if (newCost != -2) {
                upgradeBaseCost(newCost);
            }

            if (this.upgradeDescription != "" && this.upgradeDescription != null) {
                this.rawDescription = this.upgradeDescription;
            }
            initializeDescription();
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();

        this.tips.add(new TooltipInfo(MARKSTITLE,MARKSDESCRIPTION));

        return this.tips;
    }

}