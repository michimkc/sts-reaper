package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;

public class EnduringSpirit extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("EnduringSpirit");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public EnduringSpirit()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = 5;
        magicNumber = baseMagicNumber = 2;
        magicNumber2 = baseMagicNumber2 = 5;
        this.exhaust = true;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int curHP = p.maxHealth - p.currentHealth;
        int extraBlock = (int)(curHP/magicNumber2)*magicNumber;
        block = baseBlock + extraBlock;
        act(new GainBlockAction(p, p, block));

    }

    public void update()
    {
        super.update();

        int curHP = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        int extraBlock = (int)(curHP/magicNumber2)*magicNumber;
        block = baseBlock + extraBlock;
        isBlockModified = true;

        initializeDescription();
    }

    public void upgrade()
    {
        super.upgrade();
        this.isInnate = true;
    }

}
