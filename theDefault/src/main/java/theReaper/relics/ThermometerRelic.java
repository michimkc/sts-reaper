package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.powers.VengeancePower;

public class ThermometerRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "ThermometerRelic"; // set this.

    public static int bonusPercent = 10;
    private static final Logger logger = LogManager.getLogger(ThermometerRelic.class.getName());


    public ThermometerRelic() {
        super(name);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusPercent + DESCRIPTIONS[1];
    }

}
