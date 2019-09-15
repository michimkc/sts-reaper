package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.souls.AbstractSoul;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class FiendFormPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(FiendFormPower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "FiendFormPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;

    public static boolean enabled;

    // =======================

    public FiendFormPower(final AbstractCreature owner, final AbstractCreature source) {

        super(owner,source,POWER_AMOUNT,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);
        enabled = false;
    }

    public void onCardDraw(AbstractCard c)
    {
        if(enabled)
        {
            enabled = false;
            flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner,amount));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        enabled = true;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        enabled = false;
    }

    public void onUseSoul(AbstractSoul s)
    {
        enabled = true;
    }

    public void onAfterUseSoul(AbstractSoul s)
    {
        enabled = false;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FiendFormPower(owner, source);
    }
}
