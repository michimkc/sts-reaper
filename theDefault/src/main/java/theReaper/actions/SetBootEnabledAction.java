package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.patches.BootNonLethalPatch;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;

public class SetBootEnabledAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(SetBootEnabledAction.class.getName());
    boolean enabled;
    public SetBootEnabledAction(boolean pEnabled)
    {
       enabled = pEnabled;
    }

    @Override
    public void update() {
        if(AbstractDungeon.player.hasRelic("Boot")) {
            logger.info("Setting Boot to: " + enabled);
            BootNonLethalPatch.enabled.set(AbstractDungeon.player.getRelic("Boot"), enabled);
        }
        this.isDone = true;
    }



}
