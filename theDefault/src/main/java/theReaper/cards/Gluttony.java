package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.GluttonyPower;

import java.util.ArrayList;
import java.util.List;

public class Gluttony extends AbstractCustomCard {

    public static final String MARKSTITLE = BaseMod.getKeywordTitle("thereaper:marks"); // this isn't resolved but should be resolved at compile time when added by BaseMod.
    public static final String MARKSDESCRIPTION = BaseMod.getKeywordDescription("thereaper:marks");
    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    public static final String ID = DefaultMod.makeID("Gluttony");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    // Attacks hit all enemies.
    public Gluttony()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        newCost = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new GluttonyPower(p, p), magicNumber));


    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();

        this.tips.add(new TooltipInfo(MARKSTITLE,MARKSDESCRIPTION));

        return this.tips;
    }

}
