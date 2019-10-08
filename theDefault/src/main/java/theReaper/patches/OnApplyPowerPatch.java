package theReaper.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theReaper.powers.AbstractCustomPower;
import basemod.ReflectionHacks;

import java.util.ArrayList;

    @SpirePatch(
            clz= ApplyPowerAction.class,
            method="update"
    )
    public class OnApplyPowerPatch {

        public static void Prefix(ApplyPowerAction __instance) {
            if (__instance.target == null || __instance.target.isDeadOrEscaped()) {
                __instance.isDone = true;

                return;
            }

            if ((float)ReflectionHacks.getPrivate(__instance,AbstractGameAction.class,"duration") == (float)ReflectionHacks.getPrivate(__instance,ApplyPowerAction.class,"startingDuration")) {
                if ((AbstractPower)ReflectionHacks.getPrivate(__instance,ApplyPowerAction.class,"powerToApply") instanceof com.megacrit.cardcrawl.powers.NoDrawPower
                        && __instance.target.hasPower(((AbstractPower)ReflectionHacks.getPrivate(__instance,ApplyPowerAction.class,"powerToApply")).ID)) {
                    __instance.isDone = true;

                    return;
                }
                if (__instance.target != null) {
                    for (AbstractPower pow : __instance.target.powers) {
                        if(pow instanceof AbstractCustomPower) {
                            ((AbstractCustomPower)pow).onPowerApplied((AbstractPower)ReflectionHacks.getPrivate(__instance,ApplyPowerAction.class,"powerToApply"), __instance.target, __instance.source);
                        }
                    }
                }
            }
        }
    }


//}