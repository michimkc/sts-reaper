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

public class ReaperStrings
{
    public String NAME;
    public String[] DESCRIPTIONS;

    private static final Logger logger = LogManager.getLogger(ReaperStrings.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();

    public static ReaperStrings getMockReaperString() {
        ReaperStrings retVal = new ReaperStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DESCRIPTIONS = LocalizedStrings.createMockStringArray(3);
        return retVal;
    }

    public ReaperStrings getReaperStrings(String soulName) {
        if (DefaultMod.ReaperStringsMap.containsKey(soulName)) {
            return (ReaperStrings) DefaultMod.ReaperStringsMap.get(soulName);
        }
        logger.info("[ERROR] PowerString: " + soulName + " not found");
        return ReaperStrings.getMockReaperString();
    }

    public static Map<String, ReaperStrings> importReaperStrings()
    {
        logger.info("theReaper: Importing Soul Strings");
        Gson gson = new Gson();
        Settings.GameLanguage language = Settings.language;

        String reaperStrings = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Reaper-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        logger.info(reaperStrings);

        Type typeToken = new TypeToken<Map<String, ReaperStrings>>() {}.getType();

        Map reaperStringsMap = (Map) gson.fromJson(reaperStrings, typeToken);
        if (reaperStringsMap.isEmpty())
        {

            logger.info("theReaper: keywords empty??");

        } else

        {

            logger.info("reaperStrings size: " + reaperStringsMap.size());
        }

        return reaperStringsMap;

    }
}