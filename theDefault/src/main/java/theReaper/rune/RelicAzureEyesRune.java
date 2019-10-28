package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.AzureEyesRelic;

public class RelicAzureEyesRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicAzureEyesRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicAzureEyesRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(AzureEyesRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicAzureEyesRune() {
        super(new AzureEyesRelic());
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
        return DESC[0] + AzureEyesRelic.bonusBlock + DESC[1];
    }

    public RelicAzureEyesRune makeCopy()
    {
        return new RelicAzureEyesRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
