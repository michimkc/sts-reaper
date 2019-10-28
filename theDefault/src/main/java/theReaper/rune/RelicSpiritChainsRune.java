package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.CrimsonEyesRelic;
import theReaper.relics.SpiritChainsRelic;

public class RelicSpiritChainsRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicSpiritChainsRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicSpiritChainsRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(SpiritChainsRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicSpiritChainsRune() {
        super(new SpiritChainsRelic());
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
        return DESC[0] + SpiritChainsRelic.bonusWeak + DESC[1];
    }

    public RelicSpiritChainsRune makeCopy()
    {
        return new RelicSpiritChainsRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
