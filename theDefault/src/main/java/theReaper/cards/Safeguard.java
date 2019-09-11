package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theReaper.DefaultMod;

public class Safeguard extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Safeguard");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Safeguard()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = 10;

        baseMagicNumber = magicNumber = 1;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int monstercount = 0;

        for (int i = 0; i < (AbstractDungeon.getCurrRoom()).monsters.monsters.size(); i++) {
            AbstractMonster target = (AbstractMonster) (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                monstercount++;
            }
        }

        if(upgraded)
        {
            monstercount += magicNumber;
        }

        act(new GainBlockAction(p, p, block*monstercount));

    }


}
