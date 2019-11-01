package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.AnemoneRelic;
import theReaper.relics.ClawsRelic;

public class RelicAnemoneRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicAnemoneRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicAnemoneRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(AnemoneRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicAnemoneRune() {
        super(new AnemoneRelic());
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
        return DESC[0];
    }

    public RelicAnemoneRune makeCopy()
    {
        return new RelicAnemoneRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
