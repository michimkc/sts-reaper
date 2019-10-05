package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.patches.BootNonLethalPatch;
import theReaper.powers.ContagionPower;

public class SetContagionEnabledAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(SetContagionEnabledAction.class.getName());
    boolean enabled;
    AbstractCreature monster;

    public SetContagionEnabledAction(AbstractCreature m, boolean pEnabled)
    {
        monster = m;
        enabled = pEnabled;
    }

    @Override
    public void update() {
        if(monster.hasPower(DefaultMod.makeID("ContagionPower"))) {
            logger.info("Setting ContagionPower to: " + enabled);
            ((ContagionPower)monster.getPower(DefaultMod.makeID("ContagionPower"))).enabled = enabled;
        }
        this.isDone = true;
    }



}
