package theReaper.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Boot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;

@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = Boot.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class BootNonLethalPatch {
    
    private static final Logger logger = LogManager.getLogger(BootNonLethalPatch.class.getName());


    public static SpireField<Boolean> enabled = new SpireField<>(() -> true);

    @SpirePatch(
            clz=Boot.class,
            method="onAttackToChangeDamage",
            paramtypez =
                    {
                            DamageInfo.class,
                            int.class
                    }
    )
    public static class BootOnAttackToChangeDamagePatch {

        public static SpireReturn Prefix(Boot __instance, @ByRef DamageInfo[] di, @ByRef int[] damage) {

            if(!BootNonLethalPatch.enabled.get(__instance))
            {
                return SpireReturn.Return(damage[0]);
            }
            return SpireReturn.Continue();
        }
    }

}