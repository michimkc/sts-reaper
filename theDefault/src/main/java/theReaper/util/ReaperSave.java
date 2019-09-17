package theReaper.util;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.AsyncSaver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.characters.TheDefault;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.souls.AbstractSoul;
import theReaper.souls.HollowSoul;
import theReaper.souls.LostSoul;

import java.io.File;
import java.util.ArrayList;

public class ReaperSave
{
    public static String saveDir;

    public static final Logger logger = LogManager.getLogger(ReaperSave.class.getName());

    public ReaperSave()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("saves" + File.separator);

        if (CardCrawlGame.saveSlot != 0)
        {
            sb.append(CardCrawlGame.saveSlot).append("_");
        }

        sb.append("theReaper.savedsouls");

        saveDir = sb.toString();

    }

    public void saveToFile()
    {
        logger.info("Saving Souls to File...");
        String data = null;

        data = makeSoulsList();
        if(data == null)
        {
            return;
        }
        AsyncSaver.save(saveDir, data);
    }

    public String makeSoulsList()
    {
        logger.info("Making the souls list...");
        Gson gson = new Gson();
        String saveString = null;
        ArrayList<String> soulsList = new ArrayList<String>();

        if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player) == null)
        {

            logger.info("Player souls not initialized. Cannot save.");
            return null;
        }

        if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() == 0)
        {
            // dont save anything?
        } else
        {
            AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).forEach(s -> soulsList.add(s.soulName));
            saveString = gson.toJson(soulsList);
            logger.info("Printing Save String...");
            logger.info(saveString);
        }

        return saveString;
    }

    private static String loadSaveString() {

        FileHandle file = Gdx.files.local(saveDir);
        String data = file.readString();

        return data;
    }

    public void loadSaveFile()
    {
        boolean fileExists = Gdx.files.local(saveDir).exists();

        if(fileExists) {
            String savestr = null;
            Gson gson = new Gson();
            try {
                savestr = loadSaveString();
                this.onLoad(savestr);
            } catch (Exception e) {
                logger.info("Could not find the souls save file: " + saveDir);
            }
            logger.info("Save File for Souls successfully loaded.");
        } else
        {
            logger.info("File does not exist. Could not find the souls save file: " + saveDir);
        }
    }

    public void onLoad(String saveString)
    {
        logger.info("Load Souls Activated...");
        if(saveString == null)
        {
            logger.info("Save String was null. No souls to load...");
            return;
        }

        ArrayList jsonArray = new Gson().fromJson(saveString, ArrayList.class);
        ReaperSave.loadSouls(jsonArray);
    }

    public static void loadSouls(ArrayList list)
    {
        if(list != null)
        {
            list.forEach(s-> ReaperSave.addSoulToPlayerSouls((String) s));
        }
    }

    public static void addSoulToPlayerSouls(String s)
    {
        logger.info("Loading Souls from Save...");
        switch (s)
        {
            case LostSoul.soulName:
                logger.info("Adding Lost Soul to Player Souls.");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new LostSoul());
                break;
            case HollowSoul.soulName:
                logger.info("Adding Hollow Soul to Player Souls");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new HollowSoul());
                break;
            default:
                logger.info("ERROR: Invalid Soul Name when Loading Souls");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new LostSoul());
                break;
        }
        logger.info("... Loading Complete.");
    }

}

/*
public class ReaperSave implements CustomSavable<String> {

    public static final Logger logger = LogManager.getLogger(ReaperSave.class.getName());

    public Class<String> savedType() {
        return String.class;
    }


    public String onSave()
    {
        logger.info("Save Souls Activated...");
        return makeSoulsList();
    }


    public String makeSoulsList()
    {
        logger.info("Making the souls list...");
        Gson gson = new Gson();
        String saveString = null;
        ArrayList<String> soulsList = new ArrayList<String>();

        if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() == 0)
        {
            // dont save anything?
        } else
        {
            AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).forEach(s -> soulsList.add(s.soulName));
            saveString = gson.toJson(soulsList);
            logger.info("Printing Save String...");
            logger.info(saveString);
        }

        return saveString;
    }

    public void onLoad(String saveString)
    {
        logger.info("Load Souls Activated...");
        if(saveString == null)
        {
            return;
        }

        ArrayList jsonArray = new Gson().fromJson(saveString, ArrayList.class);
        ReaperSave.loadSouls(jsonArray);
    }

    public static void loadSouls(ArrayList list)
    {
        if(list != null)
        {
            list.forEach(s-> ReaperSave.addSoulToPlayerSouls((String) s));
        }
    }

    public static void addSoulToPlayerSouls(String s)
    {
        logger.info("Loading Souls from Save...");
        switch (s)
        {
            case LostSoul.soulName:
                logger.info("Adding Lost Soul to Player Souls.");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new LostSoul());
                break;
            case HollowSoul.soulName:
                logger.info("Adding Hollow Soul to Player Souls");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new HollowSoul());
                break;
            default:
                logger.info("ERROR: Invalid Soul Name when Loading Souls");
                AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).add(new LostSoul());
                break;
        }
        logger.info("... Loading Complete.");
    }
}
*/