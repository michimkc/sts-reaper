package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.SoulShifterRelic;
import theReaper.util.ReaperStrings;

public class RelicSoulShiftRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicSoulShiftRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicSoulShiftRune");
    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(SoulShifterRelic.name));
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicSoulShiftRune() {
        super(new SoulShifterRelic());
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

    public RelicSoulShiftRune makeCopy()
    {
        return new RelicSoulShiftRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
