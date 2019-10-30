package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.AzureEyesRelic;
import theReaper.relics.ThermometerRelic;

public class RelicThermometerRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicThermometerRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicThermometerRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(ThermometerRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicThermometerRune() {
        super(new ThermometerRelic());
    }

    public String getID()
    {
        return ID;
    }

    public String getName()
    {
        return name;

    }
    public String getDescription()
    {
        return DESC[0] + ThermometerRelic.bonusPercent + DESC[1];
    }

    public RelicThermometerRune makeCopy()
    {
        return new RelicThermometerRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
