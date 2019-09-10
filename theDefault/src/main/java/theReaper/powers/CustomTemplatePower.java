package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class CustomTemplatePower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(CustomTemplatePower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "CUSTOMPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    // =======================

    public CustomTemplatePower(final AbstractCreature owner, final AbstractCreature source) {

        super(owner,source,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CustomTemplatePower(owner, source);
    }
}
