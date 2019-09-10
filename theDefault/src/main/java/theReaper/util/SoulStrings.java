package theReaper.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static theReaper.DefaultMod.getModID;

public class SoulStrings
{
    public String NAME;
    public String[] DESCRIPTIONS;

    private static final Logger logger = LogManager.getLogger(SoulStrings.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();

    public static SoulStrings getMockSoulString() {
        SoulStrings retVal = new SoulStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DESCRIPTIONS = LocalizedStrings.createMockStringArray(3);
        return retVal;
    }

    public SoulStrings getSoulStrings(String soulName) {
        if (DefaultMod.SoulStringsMap.containsKey(soulName)) {
            return (SoulStrings) DefaultMod.SoulStringsMap.get(soulName);
        }
        logger.info("[ERROR] PowerString: " + soulName + " not found");
        return SoulStrings.getMockSoulString();
    }

    public static Map<String,SoulStrings> ImportSoulStrings()
    {
        logger.info("theReaper: Importing Soul Strings");
        Gson gson = new Gson();
        Settings.GameLanguage language = Settings.language;

        String soulStrings = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Soul-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        logger.info(soulStrings);

        Type typeToken = new TypeToken<Map<String, SoulStrings>>() {}.getType();

        Map soulStringsMap = (Map) gson.fromJson(soulStrings, typeToken);
        if (soulStringsMap.isEmpty())
        {

            logger.info("theReaper: keywords empty??");

        } else

        {

            logger.info("soulstrings size: " + soulStringsMap.size());
        }

        return soulStringsMap;

    }
}