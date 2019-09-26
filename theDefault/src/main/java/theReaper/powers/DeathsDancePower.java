package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.smartcardio.Card;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class DeathsDancePower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(DeathsDancePower.class.getName());

    // ==== MODIFY THESE =====
    public static final String POWER_NAME = "DeathsDancePower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;
    public static final int POWER_AMOUNT = 1;
    // =======================

    public DeathsDancePower(final AbstractCreature owner, int amount) {

        super(owner,owner,amount,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }

    public void onCardDraw(AbstractCard card) {
        ChangeCostIfStrike(card);
    }

    public void onInitialApplication()
    {
        AbstractDungeon.player.hand.group.forEach((c) -> ChangeCostIfStrike(c));
    }

    public void ChangeCostIfStrike(AbstractCard card)
    {
        if (card.hasTag(AbstractCard.CardTags.STRIKE)) {
            card.setCostForTurn(card.cost - this.amount);
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + this.amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeathsDancePower(owner, this.amount);
    }
}
