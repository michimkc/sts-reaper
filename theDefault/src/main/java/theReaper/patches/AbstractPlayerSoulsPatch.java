package theReaper.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;

/*
  This patch makes it so that IF we have the hunter's lantern, then on the map screen we reveal all the enemies
  on the next selectable nodes.
 */

@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = AbstractPlayer.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class AbstractPlayerSoulsPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    // You can have as many inner classes with patches as you want inside this one - you don't have to separate each patch into it's own file.
    // So if you need to put 4 patches all for 1 purpose (for example they all make a specific relic effect happen) - you can keep them organized together.
    // Do keep in mind that "A patch class must be a public static class."
    
    private static final Logger logger = LogManager.getLogger(AbstractPlayerSoulsPatch.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();

    public static SpireField<ArrayList<AbstractSoul>> souls = new SpireField<>(() -> new ArrayList<>());

    // amount is the minimum hp that a enemy must be before it can be soulbound
    public static final SpireField<Integer> defaultSoulBindAmount = new SpireField<Integer>(() -> 5); // default minimum HP
    public static final SpireField<Integer> soulBindAmount = new SpireField<Integer>(() -> defaultSoulBindAmount.getDefaultValue());
    public static final SpireField<Integer> baseSoulBindAmount = new SpireField<Integer>(() -> defaultSoulBindAmount.getDefaultValue());

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="combatUpdate"
    )
    public static class AbstractPlayerCombatUpdatePatch {

        public static void Postfix(AbstractPlayer __instance) {
            if (!AbstractPlayerSoulsPatch.souls.get(__instance).isEmpty()) {
                for (AbstractSoul s : AbstractPlayerSoulsPatch.souls.get(__instance)) {
                    s.update();
                }
            }
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="render"
    )
    public static class AbstractPlayerRenderPatch {

        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            if (((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT ||
                    AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) && !AbstractDungeon.player.isDead) {

                if (!AbstractPlayerSoulsPatch.souls.get(__instance).isEmpty()) {
                    for (AbstractSoul s : AbstractPlayerSoulsPatch.souls.get(__instance)) {
                        s.render(sb);
                    }
                }
            }
        }
    }

}