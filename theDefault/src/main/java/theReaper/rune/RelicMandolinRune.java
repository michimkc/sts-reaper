package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.MandolinRelic;
import theReaper.relics.SpiritChainsRelic;

public class RelicMandolinRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicMandolinRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicMandolinRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(MandolinRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicMandolinRune() {
        super(new MandolinRelic());
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
        return DESC[0] + MandolinRelic.bonusDamage + DESC[1];
    }

    public RelicMandolinRune makeCopy()
    {
        return new RelicMandolinRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
