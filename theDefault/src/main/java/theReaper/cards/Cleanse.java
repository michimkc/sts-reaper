package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theReaper.DefaultMod;

public class Cleanse extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Cleanse");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;


    public Cleanse()
    {

        super(ID, COST, TYPE, RARITY, TARGET);

        this.exhaust = true;
        newCost = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new RemoveDebuffsAction(p));

    }

    @Override
    public void upgrade()
    {
       super.upgrade();
    }


}
