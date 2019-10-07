package theReaper.rune;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

public class SoulShiftDrawRune extends AbstractSoulShiftRune{


    public static final Logger logger = LogManager.getLogger(SoulShiftDrawRune.class.getName());

    public String ID = DefaultMod.makeID("SoulShiftDrawRune");

    private ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public String[] DESC = reaperString.DESCRIPTIONS;
    public String name = reaperString.NAME;

    public int draw;

    public SoulShiftDrawRune(int drawCount) {

        this.draw = drawCount;
        logger.info("Name: " + name + " DESC: " + getDescription());
    }

    public SoulShiftDrawRune()
    {
        this.draw = 2;
    }

    public String getName()
    {
        return name;

    }
    public String getDescription()
    {
        return DESC[0] + this.draw + DESC[1];
    }

    public void onUse()
    {

        if(AbstractDungeon.player.hand.size() < 10) {
            act(new DrawCardAction(AbstractDungeon.player, draw));
        } else
        {
            AbstractDungeon.player.createHandIsFullDialog();
        }
    }

    public SoulShiftDrawRune makeCopy()
    {
        return new SoulShiftDrawRune(this.draw);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
