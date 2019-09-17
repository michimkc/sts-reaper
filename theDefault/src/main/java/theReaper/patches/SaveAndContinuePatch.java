package theReaper.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;
import theReaper.util.ReaperSave;

/*
  This patch makes it so that IF we have the hunter's lantern, then on the map screen we reveal all the enemies
  on the next selectable nodes.
 */

@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = CardCrawlGame.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class SaveAndContinuePatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    // You can have as many inner classes with patches as you want inside this one - you don't have to separate each patch into it's own file.
    // So if you need to put 4 patches all for 1 purpose (for example they all make a specific relic effect happen) - you can keep them organized together.
    // Do keep in mind that "A patch class must be a public static class."
    
    private static final Logger logger = LogManager.getLogger(SaveAndContinuePatch.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();

    @SpirePatch(
            clz= SaveAndContinue.class,
            method= "loadSaveFile",
            paramtypez={
                    String.class
            }
    )
    public static class SaveAndContinueLoadPatch {

        public static void Prefix(String filePath) {
            logger.info("Loading Save File for Reaper Souls");
            ReaperSave rs = new ReaperSave();
            rs.loadSaveFile();
        }
    }

    @SpirePatch(
            clz=SaveAndContinue.class,
            method="save",
            paramtypez={
                SaveFile.class
            }
    )
    public static class SaveAndContinueSavePatch {

        public static void Prefix(SaveFile save) {
            logger.info("Saving File for Reaper Souls");
            ReaperSave rs = new ReaperSave();
            rs.saveToFile();
        }
    }

}