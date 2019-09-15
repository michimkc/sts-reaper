package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.CrimsonTidePower;

public class CrimsonTide extends AbstractCustomCard {



    public static final String ID = DefaultMod.makeID("CrimsonTide");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public CrimsonTide()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 3;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new CrimsonTidePower(p, p, magicNumber), 1));

    }

    public void upgrade(){
        super.upgrade();
        this.isInnate = true;
    }
}
