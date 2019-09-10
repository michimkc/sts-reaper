package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import java.util.ArrayList;

import static theReaper.DefaultMod.makePowerPath;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class GluttonyPower extends AbstractCustomPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(GluttonyPower.class.getName());

    public static final String POWER_NAME = "GluttonyPower";
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean POWER_ISTURNBASED = false;

    public GluttonyPower(final AbstractCreature owner, final AbstractCreature source) {

        super(owner,source,POWER_NAME,POWER_TYPE,POWER_ISTURNBASED);

    }

    @Override
    public void onMonsterDeath(AbstractMonster m)
    {
        if(m.hasPower(DefaultMod.makeID("MarkPower")))
        {
            int markAmount = m.getPower(DefaultMod.makeID("MarkPower")).amount;

            flash();

            // the amount we heal is the number of stacks of the mark minus their current health
            int totalHeal = markAmount - m.currentHealth;

            // heal the player
            this.owner.heal(totalHeal*amount);
            // reduce the number of marks
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this, totalHeal));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GluttonyPower(owner, source);
    }
}
