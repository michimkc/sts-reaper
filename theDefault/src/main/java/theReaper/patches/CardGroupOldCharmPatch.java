package theReaper.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theReaper.DefaultMod;
import theReaper.relics.OldCharmRelic;

import java.util.ArrayList;


    @SpirePatch(
            clz= CardGroup.class,
            method="moveToExhaustPile"

    )
    public class CardGroupOldCharmPatch {

        public static SpireReturn Prefix(CardGroup __instance, @ByRef AbstractCard[] c) {
            if (AbstractDungeon.player.hasRelic(DefaultMod.makeID("OldCharmRelic"))) {
                OldCharmRelic oldCharmRelic = ((OldCharmRelic) AbstractDungeon.player.getRelic(DefaultMod.makeID("OldCharmRelic")));
                if (!oldCharmRelic.usedThisCombat) {

                    // if we haven't used it this combat
                    oldCharmRelic.usedThisCombat = true;
                    oldCharmRelic.flash();
                    oldCharmRelic.recordUse(c[0]);
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("OLDCHARM")); // play a Jingle Sound.
                    oldCharmRelic.stopPulse();

                    AbstractDungeon.player.hand.moveToDiscardPile(c[0]);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }


//}