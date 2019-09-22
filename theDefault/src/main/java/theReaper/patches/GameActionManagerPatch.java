
package theReaper.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;


@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = GameActionManager.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class GameActionManagerPatch {

    public static final Logger logger = LogManager.getLogger(GameActionManagerPatch.class.getName());

    @SpirePatch(
            clz= GameActionManager.class,
            method="getNextAction"
    )
    public static class getNextActionPatch {

        public static void Postfix (GameActionManager __instance){
            if (__instance.turnHasEnded) {
                DefaultMod.cardsDrawnThisTurn = 0;
            }

        }

    }

    @SpirePatch(
            clz= GameActionManager.class,
            method="clear"
    )
    public static class clearPatch {

        public static void Postfix (GameActionManager __instance){
                DefaultMod.cardsDrawnThisTurn = 0;
        }

    }
}


