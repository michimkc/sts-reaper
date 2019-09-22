package theReaper.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.patches.AbstractDungeonScreenPatch;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.powers.EffigyPower;
import theReaper.powers.VampirismPower;
import theReaper.souls.AbstractSoul;
import theReaper.util.SoulManager;
import theReaper.util.SoulSelectScreen;

import java.util.ArrayList;

public class EffigyAction extends CustomGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("EffigyAction");
    private static final String[] TEXT = uiStrings.TEXT;

    public static final Logger logger = LogManager.getLogger(EffigyAction.class.getName());
    SoulSelectScreen scr;
    boolean canChooseAnyNumber;

    public EffigyAction(final AbstractPlayer p, boolean anyNumber) {
        setValues(p, p, 1);

        this.actionType = ActionType.SPECIAL;
        this.duration = 0.5F;
        canChooseAnyNumber = anyNumber;

    }

    @Override
    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;

            return;
        }

        if (this.duration == 0.5F) {
            if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() > 0) {
                logger.info("Starting SoulSelectScreen.");
                scr = AbstractDungeonScreenPatch.soulSelectScreen.get(CardCrawlGame.dungeon);
                if(!canChooseAnyNumber) { // unupgraded card
                    scr.open(this, AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player), TEXT[0], 1, false, false, false);
                } else
                {
                    scr.open(this, AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player), TEXT[1], 0, true, false, false);
                }

            } else
            {
                logger.info("No souls in player souls list. Ending action.");
                this.isDone = true;
            }

        }

        tickDuration();

    }

    public void soulSelectResult(ArrayList<AbstractSoul> soulList)
    {
        logger.info("SoulSelectScreen returned soulList at end of selection screen. Amount was " + soulList.size());
        if(soulList.size() > 0)
        {
            logger.info("Consumed " + soulList.size() + " souls. Adding that many stacks of Effigy to the player.");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(source, source, new EffigyPower(source, source, soulList.size()), soulList.size()));

            soulList.forEach(s -> s.consumeSoul());
        }
        logger.info("Finishing up.");
        SoulManager.resetSelectionMode();
        SoulManager.updateSoulIndices();
    }

}
