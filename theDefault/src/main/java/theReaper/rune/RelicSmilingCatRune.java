package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.CrimsonEyesRelic;
import theReaper.relics.SmilingCatRelic;

public class RelicSmilingCatRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicSmilingCatRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicSmilingCatRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(SmilingCatRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicSmilingCatRune() {
        super(new SmilingCatRelic());
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
        return DESC[0] + SmilingCatRelic.bonusEnergy + DESC[1];
    }

    public RelicSmilingCatRune makeCopy()
    {
        return new RelicSmilingCatRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
