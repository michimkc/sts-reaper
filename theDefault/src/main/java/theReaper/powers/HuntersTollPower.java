package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;

import static theReaper.cards.AbstractCustomCard.act;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class HuntersTollPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(HuntersTollPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "HuntersTollPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    //public static final int POWER_AMOUNT = 1;
    // =======================

    public HuntersTollPower(final AbstractCreature owner, final AbstractCreature source, int amount) {

        super(owner,source,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }

    public void onApplyMarks(AbstractCreature target, AbstractCreature source, int totalAmount)
    {
        flash();
        MarkPower.applyMarks(target,source,this.amount);
    }

    public void onInitialApplication()
    {
        flash();
        //if player doesnt have vengeance, give them vengeance.
        if(!AbstractDungeon.player.hasPower(DefaultMod.makeID("VengeancePower")))
        {
            act(new ApplyPowerAction(owner, owner, new VengeancePower(owner, owner, 1), 1));
        }
    }

    public void onPowerRemoved(AbstractCustomPower p) {

        if (p instanceof VengeancePower) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new HuntersTollPower(owner, source, amount);
    }
}
