package theReaper.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.util.SoulSelectScreen;

import java.util.ArrayList;


@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = AbstractDungeon.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class AbstractDungeonScreenPatch {

    private static final Logger logger = LogManager.getLogger(AbstractDungeonScreenPatch.class.getName());

    public static SpireField<SoulSelectScreen> soulSelectScreen = new SpireField<>(() -> new SoulSelectScreen());



    @SpirePatch(
            clz=AbstractDungeon.class,
            method="update"
    )
    public static class AbstractDungeonPostFixScreenUpdatePatch {

        public static void Postfix(AbstractDungeon __instance) {

            if(AbstractDungeon.screen == SoulSelectEnum.SOULSELECTSCREEN) {
                logger.info("current screen is soul select screen. updating.");
                AbstractDungeonScreenPatch.soulSelectScreen.get(CardCrawlGame.dungeon).update();
            }
        }
    }


    @SpirePatch(
            clz=AbstractDungeon.class,
            method="openPreviousScreen",
            paramtypez = {
                    AbstractDungeon.CurrentScreen.class
            }
    )
    public static class AbstractDungeonPreviousScreenPatch {

        public static void Postfix(@ByRef AbstractDungeon.CurrentScreen[] s) {

            if(s[0] == SoulSelectEnum.SOULSELECTSCREEN) {
                logger.info("current screen is soul select screen. Reopening.");
                AbstractDungeonScreenPatch.soulSelectScreen.get(CardCrawlGame.dungeon).reopen();
            }
        }
    }



    @SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractDungeon.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "render"
    )
    public static class RenderInserterPatch {

        @SpireInsertPatch( // This annotation of our patch method specifies the type of patch we will be using. In our case - a Spire Insert Patch

                locator = UpdateLocator.class, // Spire insert patches require a locator - this isn't something you import - this is something we write.
                // (Or as is usually the case with them - copy paste cause they're always nearly the same thing.
                // In fact, most insert patches are fairly boiler-plate. You could easily make an insert patch template, if you'd like.)
                // You can find our Locator class just below, as an inner class, underneath our actual patch method.

                localvars = {} // The method we're patching, returnRandomRelicKey(), has a local variable that we'd like to access and manipulate -
                // "String retVal = null;". So, we simply write out it's name here and then add it as a parameter to our patch method.
                // Keep in mind that localvars can also be used to capture class variables, not just local method ones. This also includes private ones.
        )
        //"A patch method must be a public static method."
        public static void UpdatePatch(AbstractDungeon __instance, SpriteBatch sb) {

            // Wow time to actually put stuff in the basegame code!!! Everything here will be executed exactly as written, at the line which we specified.
            // You can change retVal (using @byRef) to always return the same relic, or return a specific relic if it passes some check.
            // You can execute any other static method you have, you can save retVal to your personal public static variable to always be able to
            // reference the last relic you grabbed - etc. etc. The possibilities are endless. We're gonna do the following:

            if(AbstractDungeon.screen == SoulSelectEnum.SOULSELECTSCREEN) {
                logger.info("current screen is soul select screen. rendering.");
                AbstractDungeonScreenPatch.soulSelectScreen.get(CardCrawlGame.dungeon).render(sb);
            }
        }
        private static class UpdateLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {


                Matcher finalMatcher = new Matcher.MethodCallMatcher(OverlayMenu.class, "renderBlackScreen");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }
    }
}
