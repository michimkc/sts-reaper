package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.CrimsonEyesRelic;
import theReaper.relics.FangsRelic;

public class RelicFangsRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicFangsRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicFangsRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(FangsRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicFangsRune() {
        super(new FangsRelic());
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
        return DESC[0] + FangsRelic.bonusHealth + DESC[1];
    }

    public RelicFangsRune makeCopy()
    {
        return new RelicFangsRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
