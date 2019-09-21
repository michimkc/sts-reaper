
package theReaper.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.cards.AbstractCustomCard;

@SpirePatch(
            clz= AbstractPlayer.class,
            method=SpirePatch.CLASS
    )
    public class AbstractPlayerCardDrawPatch {

        public static final Logger logger = LogManager.getLogger(AbstractPlayerCardDrawPatch.class.getName());


    @SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
            clz = AbstractPlayer.class, // This is the class where the method we will be patching is. In our case - Abstract Dungeon
            method = "draw",
            paramtypez = {
                    int.class
            }
    )
    public static class DrawInsertPatch {

        @SpireInsertPatch(
                locator = UpdateLocator.class,

                localvars = {}
        )
        public static void UpdatePatch(AbstractPlayer __instance) {
            logger.info("Triggering onCardDraw for AbstractCustomCard");
            for( AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if(c instanceof AbstractCustomCard)
                {
                    ((AbstractCustomCard) c).onCardDraw();
                }
            }

        }
        private static class UpdateLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerWhenDrawn");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }
    }
/*
        @SpirePatch(
                clz = AbstractPlayer.class,
                method = "draw",
                paramtypez = {
                        int.class
                }
        )
        public static class DrawPatch {
            public static void PostFix(AbstractPlayer __instance, @ByRef int numCards)
            {
                for( AbstractCard c : AbstractDungeon.player.hand.group)
                {
                    if(c instanceof AbstractCustomCard)
                    {
                        ((AbstractCustomCard) c).onCardDraw();
                    }
                }
            }
        }
        */

    }


