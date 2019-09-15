/*
package theReaper.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.souls.LostSoul;

import java.util.ArrayList;

    @SpirePatch(
            clz= DrawCardAction.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez={
                    AbstractCreature.class,
                    int.class
            }
    )
    public class DrawCardActionPatch {

        public static final Logger logger = LogManager.getLogger(DrawCardActionPatch.class.getName());

        public static void Prefix(DrawCardAction __instance, @ByRef AbstractCreature[] source, @ByRef int[] amount) {

            logger.info("Draw");
            if (AbstractDungeon.player.hasPower(DefaultMod.makeID("FiendFormPower")))
            {
                logger.info("Draw");
                amount[0] += AbstractDungeon.player.getPower(DefaultMod.makeID("FiendFormPower")).amount;
            }

        }
    }


//}*/