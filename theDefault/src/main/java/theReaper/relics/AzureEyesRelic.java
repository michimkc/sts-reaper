package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AzureEyesRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "AzureEyesRelic"; // set this.

    public static int bonusBlock = 3;
    private static final Logger logger = LogManager.getLogger(AzureEyesRelic.class.getName());


    public AzureEyesRelic() {
        super(name);
    }

    @Override
    public void onConsumeMarks(AbstractMonster m, int totalHeal) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,bonusBlock));
        flash();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusBlock + DESCRIPTIONS[1];
    }

}
