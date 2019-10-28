package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

public class SilverBeadsRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "SilverBeadsRelic"; // set this.
    public static final int bonusVulnerable = 1;

    private static final Logger logger = LogManager.getLogger(SilverBeadsRelic.class.getName());


    public SilverBeadsRelic() {
        super(name);
    }


    @Override
    public void onSoulBind(AbstractMonster m, boolean bindEnemy) {

        if(!bindEnemy) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, bonusVulnerable, false), bonusVulnerable));
            flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusVulnerable + DESCRIPTIONS[1];
    }

}
