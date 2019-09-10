package theReaper.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theReaper.DefaultMod;
import theReaper.powers.AbstractCustomPower;

import java.util.ArrayList;

@SpirePatch(
        clz=AbstractMonster.class,
        method="die",
        paramtypez={
        boolean.class
        }
)
public class AbstractMonsterPatch {

    public static void Prefix(AbstractMonster m, boolean triggerRelics) {
        if (!m.isDying) {

            if (m.currentHealth <= 0 &&
                    triggerRelics) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof AbstractCustomPower) {
                        ((AbstractCustomPower) p).onMonsterDeath(m); // add a new trigger
                    }
                }
            }
        }

    }
}

