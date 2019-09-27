package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.BloodMoonPower;
import theReaper.powers.VengeancePower;

import java.util.ArrayList;
import java.util.List;

public class BloodMoon extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("BloodMoon");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public BloodMoon()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 3;
        newCost = 1;
        baseMagicNumber2 = magicNumber2 = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new ApplyPowerAction(p, p, new BloodMoonPower(p, p, magicNumber), magicNumber));
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

}
