package theReaper.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.HuntersTollPower;

public class HuntersToll extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("HuntersToll");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public HuntersToll()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 5;
        newCost = 1;
        baseMagicNumber2 = magicNumber2 = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new HuntersTollPower(p, p, magicNumber), magicNumber));
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

}
