package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.ShadowStepPower;
import theReaper.powers.VampirismPower;

import java.util.ArrayList;

public class ShadowStep extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("ShadowStep");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;


    public ShadowStep()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        flash();
        act(new ApplyPowerAction(p, p, new ShadowStepPower(p, p, 1,magicNumber), 1));

    }

    public void upgrade() {
        super.upgrade();

        this.exhaust = false;

    }
}
