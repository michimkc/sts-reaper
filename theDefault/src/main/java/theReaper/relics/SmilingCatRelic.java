package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.powers.MarkPower;

public class SmilingCatRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "SmilingCatRelic"; // set this.

    public static int bonusEnergy = 1;
    private static final Logger logger = LogManager.getLogger(SmilingCatRelic.class.getName());


    public SmilingCatRelic() {
        super(name);
    }

    @Override
    public void onRemoveMarks(AbstractMonster m, boolean expired) {
        if(!expired)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(bonusEnergy));
            flash();

        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusEnergy + DESCRIPTIONS[1];
    }

}
