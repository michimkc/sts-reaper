package theReaper.util;

import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.RemoveSoulAction;
import theReaper.cards.AbstractCustomCard;
import theReaper.characters.TheDefault;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.souls.AbstractSoul;
import theReaper.souls.HollowSoul;
import theReaper.souls.LostSoul;

import java.util.ArrayList;
import java.util.List;

public class SoulManager {

    public static final Logger logger = LogManager.getLogger(SoulManager.class.getName());
    public static float spacerWidth = 5f;

    // soulbind tooltip
    public static final String SOULBIND_NAME = "SoulBind";
    public static final String SOULBIND_ID = DefaultMod.makeID(SOULBIND_NAME);
    private static final SoulStrings soulString = DefaultMod.SoulStringsMap.get(SOULBIND_ID);
    public static final String[] SOULBIND_DESC = soulString.DESCRIPTIONS;
    // end soulbind tooltip

    private static final SoulStrings soulManagerStrings = DefaultMod.SoulStringsMap.get(DefaultMod.makeID("SoulManager"));
    public static final String[] MSG = soulManagerStrings.DESCRIPTIONS;

    public static void addSoul(AbstractSoul soul)
    {
        // check if we've reached max number of souls
        if (AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() == TheDefault.SOUL_SLOTS)
        {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, MSG[0], true));
            return;
        }

        // add soul.
        AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(soul);
        soul.tY = 680;

        updateSoulIndices();
        printSoulList();
    }

    public static float xPosAtIndex(int index)
    {
        return (40+(spacerWidth+AbstractSoul.textureWidth)*(index));
    }

    public static void updateSoulIndices(){
        if(!AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).isEmpty()) {
            for (int i = 0; i < AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size(); i++) {
                AbstractSoul s = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).get(i);
                s.tX = SoulManager.xPosAtIndex(i);
                s.index = i;
            }
            logger.info("Soul ArrayList Count: " + AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size());
        } else
        {
            logger.info("Soul ArrayList is Empty");
        }
    }

    public static void RemoveSoul(AbstractSoul soul)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSoulAction(AbstractDungeon.player,soul));
    }

    public static void printSoulList()
    {
        logger.info("Current list of souls:");
        ArrayList<AbstractSoul> soulList = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player);
        if(!soulList.isEmpty())
        {
            soulList.forEach((s)-> logger.info(s.name));

        } else
        {
            logger.info("Soul List is empty");
        }
    }

    public static List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();
        tips.clear();

        String tooltipString = SOULBIND_DESC[0] + AbstractPlayerSoulsPatch.soulBindAmount.get(AbstractDungeon.player) + SOULBIND_DESC[1];

        tips.add(new TooltipInfo(SOULBIND_NAME, tooltipString));

        return tips;
    }

    public static List<TooltipInfo> getCustomTooltips(int soulBindAmount) {
        ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();
        tips.clear();

        String tooltipString = SOULBIND_DESC[0] + soulBindAmount + SOULBIND_DESC[1];

        tips.add(new TooltipInfo(SOULBIND_NAME, tooltipString));

        return tips;
    }


}
