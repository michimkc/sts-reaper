package theReaper.patches;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.characters.TheDefault;
import theReaper.rune.RunePageMenuButton;
import theReaper.util.SoulSelectScreen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = TopPanel.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class TopPanelPatch {

    private static final Logger logger = LogManager.getLogger(TopPanelPatch.class.getName());

    public static SpireField<RunePageMenuButton> runeButton = new SpireField<>(() -> new RunePageMenuButton());

    /*
    @SpirePatch(
            clz=TopPanel.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class constructorPatch {

        public static void Postfix(TopPanel __instance)
        {

        }
    }*/

    @SpirePatch(
            clz=TopPanel.class,
            method="updateSettingsButtonLogic"
    )
    public static class updateSettingsButtonLogicPatch {

        public static void Prefix(TopPanel __instance) {

            if (AbstractDungeon.screen == SoulSelectEnum.SOULSELECTSCREEN) {
               AbstractDungeon.previousScreen = SoulSelectEnum.SOULSELECTSCREEN;
            }
        }
    }

    @SpirePatch(
            clz=TopPanel.class,
            method="render"
    )
    public static class renderPatch {

        public static void Postfix(TopPanel __instance, @ByRef SpriteBatch[] sb) {

            if (AbstractDungeon.player instanceof TheDefault)
            {
                if(TopPanelPatch.runeButton.get(AbstractDungeon.topPanel) != null)
                {
                    TopPanelPatch.runeButton.get(AbstractDungeon.topPanel).render(sb[0]);
                }
            }
        }
    }

}
