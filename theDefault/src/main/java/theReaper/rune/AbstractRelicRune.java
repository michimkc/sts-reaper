package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.relics.OldCharmRelic;
import theReaper.util.ReaperStrings;

public abstract class AbstractRelicRune extends AbstractRune {


    public static final Logger logger = LogManager.getLogger(AbstractRelicRune.class.getName());

    public static String ID = DefaultMod.makeID("AbstractRelicRune");

    private ReaperStrings reaperString;// = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC;// = reaperString.DESCRIPTIONS;
    public String name;

    public AbstractRelic relic;

    public AbstractRelicRune(AbstractRelic relic) {
        super();
        this.relic = relic;
    }


    public void onUse()
    {
        Circlet circlet;
        if (!AbstractDungeon.player.hasRelic(this.relic.relicId)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, relic);
        } else {
            circlet = new Circlet();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, circlet);
        }
    }

    public abstract AbstractRelicRune makeCopy();

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
