package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.CrimsonEyesRelic;

public class RelicCrimsonEyesRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicCrimsonEyesRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicCrimsonEyesRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(CrimsonEyesRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicCrimsonEyesRune() {
        super(new CrimsonEyesRelic());
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
        return DESC[0] + CrimsonEyesRelic.bonusMarks + DESC[1];
    }

    public RelicCrimsonEyesRune makeCopy()
    {
        return new RelicCrimsonEyesRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
