package theReaper.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;

/*
  This patch makes it so that IF we have the hunter's lantern, then on the map screen we reveal all the enemies
  on the next selectable nodes.
 */

/*
@SpirePatch(    // "Use the @SpirePatch annotation on the patch class."
        clz = DamageAction.class, // This is the class where the method we will be patching is.
        method = SpirePatch.CLASS // This is the name of the method we will be patching.

)
public class DamageActionPatch {// Don't worry about the "never used" warning - *You* usually don't use/call them anywhere. Mod The Spire does.
    
    // You can have as many inner classes with patches as you want inside this one - you don't have to separate each patch into it's own file.
    // So if you need to put 4 patches all for 1 purpose (for example they all make a specific relic effect happen) - you can keep them organized together.
    // Do keep in mind that "A patch class must be a public static class."
    
    private static final Logger logger = LogManager.getLogger(DamageActionPatch.class.getName()); // This is our logger! It prints stuff out in the console.
    // It's like a very fancy System.out.println();
*/
    @SpirePatch(
            clz=DamageAction.class,
            method="update"
    )
    public class DamageActionPatch {

        public static void Prefix(DamageAction da) {
            if (AbstractDungeon.player.hasPower(DefaultMod.makeID("SavageFormPower"))){

                DamageInfo.DamageType dt = da.damageType;
                ArrayList<AbstractMonster> m = (AbstractDungeon.getCurrRoom()).monsters.monsters;

                int[] multiDamage = new int[m.size()];
                for (int i = 0; i < multiDamage.length; i++) {

                    multiDamage[i] = da.amount;
                }
                // usurp the damage call.
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, dt, AbstractGameAction.AttackEffect.NONE));
                da.isDone = true;
                return;
            }

        }
    }


//}