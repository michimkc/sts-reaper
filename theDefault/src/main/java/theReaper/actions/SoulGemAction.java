package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.cards.AbstractCustomCard;
import theReaper.souls.AbstractSoul;
import theReaper.util.SoulManager;

public class SoulGemAction extends AbstractGameAction {

    private AbstractSoul soul;
    public static final Logger logger = LogManager.getLogger(SoulGemAction.class.getName());
    public float durationDefault = 0.5f;

    public SoulGemAction(AbstractSoul pSoul) {

        this.soul = pSoul;
        this.duration = durationDefault;

    }

    @Override
    public void update() {

        if (this.duration == durationDefault) {

            logger.info("Adding Soul: " + this.soul.name);
            SoulManager.addSoul(this.soul);
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
               if(c instanceof AbstractCustomCard)
               {
                   ((AbstractCustomCard) c).onSoulAdded(this.soul);
                   ((AbstractCustomCard) c).onSoulCountChanged();
               }
            }
        }

        tickDuration();

    }
}
