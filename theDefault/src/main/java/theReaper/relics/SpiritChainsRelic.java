package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

public class SpiritChainsRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "SpiritChainsRelic"; // set this.
    public static final int bonusWeak = 1;

    private static final Logger logger = LogManager.getLogger(SpiritChainsRelic.class.getName());


    public SpiritChainsRelic() {
        super(name);
    }

    @Override
    public void onSoulBind(AbstractMonster m, boolean bindEnemy) {
        if(!bindEnemy) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, bonusWeak, false), bonusWeak));
            flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusWeak + DESCRIPTIONS[1];
    }

}
