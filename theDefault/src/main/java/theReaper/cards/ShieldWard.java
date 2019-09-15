package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.BleedLoseHpAction;
import theReaper.powers.BleedPower;

public class ShieldWard extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("ShieldWard");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public ShieldWard()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = 24;

        baseSelfBleedNumber = selfBleedNumber = 2;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new GainBlockAction(p, p, block));
        act(new ApplyPowerAction(p, p, new BleedPower(p, p, selfBleedNumber), selfBleedNumber));

    }

    public void upgrade(){
        super.upgrade();
        this.isInnate = true;
    }

}
