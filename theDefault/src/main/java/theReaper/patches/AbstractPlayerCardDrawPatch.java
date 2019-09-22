/*
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


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onCardDrawOrDiscard"

    )
    public static class onCardDrawOrDiscardPatch {
        public static void PostFix(AbstractPlayer __instance) {
            logger.info("Card Drawn. Calling AbstractCustomCard onCardDraw");
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractCustomCard) {
                    ((AbstractCustomCard) c).onCardDraw();
                }
            }
        }
    }
}
*/




