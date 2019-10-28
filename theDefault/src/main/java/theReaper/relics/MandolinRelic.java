package theReaper.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.patches.AbstractPlayerSoulsPatch;

import static theReaper.cards.AbstractNonLethalCard.NonLethalDamageAction;

public class MandolinRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "MandolinRelic";

    private static final Logger logger = LogManager.getLogger(MandolinRelic.class.getName());

    public static int bonusDamage = 5;

    public MandolinRelic() {
        super(name);
    }


    @Override
    public void onSoulBind(AbstractMonster m, boolean bindEnemy) {

        if(!bindEnemy) {
            NonLethalDamageAction(m, new DamageInfo(AbstractDungeon.player, bonusDamage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL, false);
            flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusDamage + DESCRIPTIONS[1];
    }

}
