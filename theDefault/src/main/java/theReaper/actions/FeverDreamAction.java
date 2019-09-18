package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.patches.AbstractDungeonScreenPatch;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.souls.AbstractSoul;
import theReaper.util.SoulManager;
import theReaper.util.SoulSelectScreen;

import java.util.ArrayList;

public class FeverDreamAction extends CustomGameAction {


    public static final Logger logger = LogManager.getLogger(FeverDreamAction.class.getName());
    public int energyPerSoul = 0;
    SoulSelectScreen scr;

    public FeverDreamAction(final AbstractPlayer p, int energyPerSoul) {
        setValues(p, p, 0);

        this.actionType = ActionType.SPECIAL;
        this.duration = 0.5F;
        this.energyPerSoul = energyPerSoul;

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
                scr.open(this,AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player),"Gain Energy for Each Soul Selected",0,true,true,true);

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
            logger.info("Gaining " + soulList.size() + "energy. Consuming that many souls.");
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(soulList.size()));
            soulList.forEach(s -> s.consumeSoul());
        }
        logger.info("Finishing up.");
        SoulManager.resetSelectionMode();
        SoulManager.updateSoulIndices();
    }

}
