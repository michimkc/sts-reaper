package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.ClawsRelic;
import theReaper.relics.FadedLocketRelic;

public class RelicFadedLocketRune extends AbstractRelicRune{


    public static final Logger logger = LogManager.getLogger(RelicFadedLocketRune.class.getName());

    public static String ID = DefaultMod.makeID("RelicFadedLocketRune");

    private RelicStrings reaperString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(FadedLocketRelic.name));

    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public RelicFadedLocketRune() {
        super(new FadedLocketRelic());
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
        return DESC[0] + FadedLocketRelic.percentHealth + DESC[1] + FadedLocketRelic.bonusUndying + DESC[2];
    }

    public RelicFadedLocketRune makeCopy()
    {
        return new RelicFadedLocketRune();
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
