package theReaper.util;

import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.RemoveSoulAction;
import theReaper.characters.TheDefault;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.relics.SilverBeadsRelic;
import theReaper.relics.SpiritChainsRelic;
import theReaper.rune.AbstractSoulShiftRune;
import theReaper.souls.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SoulManager implements CustomSavable<String> {

    public static final Logger logger = LogManager.getLogger(SoulManager.class.getName());
    public static float spacerWidth = 20f;
    public static float defaultHeight = Settings.HEIGHT/2.0F + 160.0F * Settings.scale;

    // soulbind tooltip
    public static final String SOULBIND_NAME = "SoulBind";
    public static final String SOULBIND_ID = DefaultMod.makeID(SOULBIND_NAME);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(SOULBIND_ID);
    public static final String[] SOULBIND_DESC = reaperString.DESCRIPTIONS;
    // end soulbind tooltip

    private static final ReaperStrings soulManagerStrings = DefaultMod.ReaperStringsMap.get(DefaultMod.makeID("SoulManager"));
    public static final String[] MSG = soulManagerStrings.DESCRIPTIONS;

    public String soulsSaveList; // we will save this

    public static void useSoul()
    {
        DefaultMod.currentShiftRune.onUse();
    }

    public static void addSoul(AbstractSoul soul)
    {
        ArrayList<AbstractSoul> soulsList = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player);
        // check if we've reached max number of souls
        if (soulsList.size() == TheDefault.SOUL_SLOTS)
        {
            boolean removedLesserSoul = false;
            if(soul instanceof KingSoul)
            {
                for(int i = 0; i<soulsList.size(); i++)
                {
                    if(soulsList.get(i) instanceof LostSoul)
                    {
                        soulsList.remove(i);
                        removedLesserSoul = true;
                        break;
                    }
                }

                if (!removedLesserSoul)
                {

                    for(int i = 0; i<soulsList.size(); i++)
                    {
                        if(soulsList.get(i) instanceof HollowSoul)
                        {
                            RemoveSoul(soulsList.get(i),true);
                            removedLesserSoul = true;
                            break;
                        }
                    }
                }
            }

            if(soul instanceof HollowSoul)
            {
                for(int i = 0; i<soulsList.size(); i++)
                {
                    if(soulsList.get(i) instanceof LostSoul)
                    {
                        RemoveSoul(soulsList.get(i), true);
                        removedLesserSoul = true;
                        break;
                    }
                }
            }

            if(!removedLesserSoul) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, MSG[0], true));
                return;
            }

        }

        // add soul.
        AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(soul);
        soul.tY = defaultHeight;

        int nextUUID = AbstractPlayerSoulsPatch.soulUUIDCount.get(AbstractDungeon.player);
        soul.uuid = nextUUID;
        nextUUID++;
        AbstractPlayerSoulsPatch.soulUUIDCount.set(AbstractDungeon.player, nextUUID); // increment the uuid.

        updateSoulIndices();
        printSoulList();
    }

    public static float xPosAtIndex(int index)
    {
        return (150+(spacerWidth+AbstractSoul.textureWidth)*(index))*Settings.scale;
    }

    public static void updateSoulIndices(){
        if(!AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).isEmpty()) {
            for (int i = 0; i < AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size(); i++) {
                AbstractSoul s = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).get(i);
                s.tX = SoulManager.xPosAtIndex(i);
                s.tY = defaultHeight;
                s.index = i;
            }
        } else
        {
            logger.info("Soul ArrayList is Empty");
        }
    }

    public static void RemoveSoul(AbstractSoul soul)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSoulAction(AbstractDungeon.player,soul));

    }

    public static void RemoveSoul(AbstractSoul soul, boolean noDuration) // noDuration should be true to make the removal instant.
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSoulAction(AbstractDungeon.player,soul, noDuration));
    }

    public static void soulShift(AbstractSoulShiftRune rune)
    {
        DefaultMod.currentShiftRune = rune;
        if (DefaultMod.soulTip == null)
        {
            DefaultMod.soulTip = new SoulTip();
        }
        DefaultMod.soulTip.LoadSoulShiftRune();

    }


    public static void renderSoulTip(SpriteBatch sb)
    {
        if (DefaultMod.soulTip == null)
        {
            DefaultMod.soulTip = new SoulTip();
        }
        DefaultMod.soulTip.render(sb);
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
        if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(SpiritChainsRelic.name)))
        {
            tooltipString += SOULBIND_DESC[2] + SpiritChainsRelic.bonusWeak + SOULBIND_DESC[3];
        }
        if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(SilverBeadsRelic.name)))
        {
            tooltipString += SOULBIND_DESC[4] + SilverBeadsRelic.bonusVulnerable + SOULBIND_DESC[5];
        }

        tips.add(new TooltipInfo(SOULBIND_NAME, tooltipString));

        return tips;
    }

    public static void resetSelectionMode()
    {
        ArrayList<AbstractSoul> soulList = AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player);
        if(soulList.size() > 0) {
            soulList.forEach(s -> s.inSelectionScreen = false);
            soulList.forEach(s -> s.currentSelectScreen = null);
        }
    }

    @Override
    public String onSave()
    {
        return makeSoulsList();
    }

    public Class<String> savedType() {
        return String.class;
    }


    public String makeSoulsList()
    {
        Gson gson = new Gson();
        String saveString = null;
        ArrayList<String> soulsList = new ArrayList<String>();

        if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() == 0)
        {
            // dont save anything?
        } else
        {
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).forEach(s -> soulsList.add(s.getSoulName()));
            logger.info("Printing soul names...");
            saveString = gson.toJson(soulsList, listType);
            logger.info("Printing Save String...");
            logger.info(saveString);
        }

        return saveString;
    }

    public void onLoad(String saveString)
    {
        if(saveString == null)
        {
            return;
        }

        logger.info("Loaded Souls String: " + saveString);

        ArrayList<String> jsonArray = new Gson().fromJson(saveString, ArrayList.class);
        SoulManager.loadSouls(jsonArray);
    }

    public static void loadSouls(ArrayList<String> list)
    {
        if(list != null)
            {
            list.forEach(s->SoulManager.addSoulToPlayerSouls(s));
        }
    }

    public static void addSoulToPlayerSouls(String s)
    {
        logger.info("Loading Souls from Save...");
        if(s == null)
        {
            logger.info("Saved Soul was NULL. Could not add soul...");

            return;
        }
        switch (s)
        {
            case LostSoul.soulName:
                logger.info("Adding Lost Soul to Player Souls.");
                addSoul(new LostSoul());
                break;
            case HollowSoul.soulName:
                logger.info("Adding Hollow Soul to Player Souls");
                addSoul(new HollowSoul());
                break;
            case KingSoul.soulName:
                logger.info("Adding King Soul to Player Souls");
                addSoul(new KingSoul());
                break;
            default:
                logger.info("ERROR: Invalid Soul Name when Loading Souls");
                addSoul(new LostSoul());
                break;
        }
        logger.info("... Loading Complete.");
    }
}
