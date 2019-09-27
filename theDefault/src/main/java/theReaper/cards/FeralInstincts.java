package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.FeralInstinctsPower;

import java.util.ArrayList;

public class FeralInstincts extends AbstractCustomCard {

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    public static final String ID = DefaultMod.makeID("FeralInstincts");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    // Attacks hit all enemies.
    public FeralInstincts()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new FeralInstinctsPower(p), magicNumber));


    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.isInnate = true;
    }
}
