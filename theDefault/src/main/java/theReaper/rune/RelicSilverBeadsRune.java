package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.SilverBeadsRelic;

public class RelicSilverBeadsRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicSilverBeadsRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicSilverBeadsRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(SilverBeadsRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicSilverBeadsRune() {
        super(new SilverBeadsRelic());
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
        return DESC[0] + SilverBeadsRelic.bonusVulnerable + DESC[1];
    }

    public RelicSilverBeadsRune makeCopy()
    {
        return new RelicSilverBeadsRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
