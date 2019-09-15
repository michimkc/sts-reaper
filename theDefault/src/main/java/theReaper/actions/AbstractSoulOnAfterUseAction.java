package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.cards.AbstractCustomCard;
import theReaper.souls.AbstractSoul;
import theReaper.util.SoulManager;

public class AbstractSoulOnAfterUseAction extends AbstractGameAction {

    private AbstractSoul soul;
    public static final Logger logger = LogManager.getLogger(AbstractSoulOnAfterUseAction.class.getName());


    public AbstractSoulOnAfterUseAction(AbstractSoul pSoul) {

        this.soul = pSoul;

    }

    @Override
    public void update() {
        AbstractSoul.notifyOnAfterUseSoul(this.soul);
        this.isDone = true;
    }
}
