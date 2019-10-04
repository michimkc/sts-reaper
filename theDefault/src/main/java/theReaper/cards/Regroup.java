package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.PutOnDeckChooseAction;
import theReaper.powers.BleedPower;

import java.util.ArrayList;

public class Regroup extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Regroup");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Regroup() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;

        baseMagicNumber2 = magicNumber2 = 1;
        magicNumber2Up = 1;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DrawCardAction(p,magicNumber)); // draw cards
        act(new PutOnDeckChooseAction(p,p,magicNumber2,false,true)); // place one ( up to 2 ) cards on top of deck
    }


}