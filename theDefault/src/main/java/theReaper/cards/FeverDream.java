package theReaper.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.FeverDreamAction;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.powers.BleedPower;

public class FeverDream extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("FeverDream");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public FeverDream() {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumberUp = 1;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int energyAdd = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size();
        if(upgraded)
        {
            energyAdd++;
        }
        act(new GainEnergyAction(energyAdd));


    }


}