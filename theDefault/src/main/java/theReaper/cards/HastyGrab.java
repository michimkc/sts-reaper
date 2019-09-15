package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

import java.util.ArrayList;

public class HastyGrab extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("HastyGrab");

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public HastyGrab() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        magicNumberUp = 1;

        baseSelfBleedNumber = selfBleedNumber = 2; // bleed

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DrawCardAction(p,magicNumber)); // draw cards
        act(new ApplyPowerAction(p,p,new BleedPower(p,p,selfBleedNumber), selfBleedNumber)); // self bleed
    }


}