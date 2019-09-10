package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makePowerPath;

//When affected by vengeance, each damage taken adds a mark to the enemy that dealt it.
//dealing damage to the enemy consumes a mark and heals 1 hp.
//marks reduce by half the total, or 5, each turn, whichever is larger.

public class MarkPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public static final Logger logger = LogManager.getLogger(MarkPower.class.getName());

    public static final String POWER_ID = DefaultMod.makeID("MarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("mark_placeholder84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("mark_placeholder32.png"));

    // reduce the marks by PERCENTREDUCTION, CONSTANTREDUCTION whichever is greater.
    private static final float PERCENTREDUCTION = 0.5f;
    private static final int CONSTANTREDUCTION = 5;


    public MarkPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {

            flash();

            // the max that we can heal is the number of stacks of the mark
            int totalHeal = Math.min(damageAmount, amount);
            // but make sure we dont heal more than the creature's max hp
            totalHeal = Math.min(totalHeal, owner.currentHealth);

            // heal the player
            info.owner.heal(totalHeal);
            // reduce the number of marks
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this, totalHeal));
        }

        return damageAmount;
    }

    public void atStartOfTurn() {

            // at the start of the monster's, reduce the marks by PERCENTREDUCTION, CONSTANTREDUCTION whichever is greater.

            // if we have fewer marks than the minimum amount that we reduce by, remove the buff.
            if (CONSTANTREDUCTION >= amount) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            } else {
                int percentAmount = Math.round(amount * PERCENTREDUCTION);
                int amountToReduce = Math.max(percentAmount, CONSTANTREDUCTION);

                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, amountToReduce));
            }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + CONSTANTREDUCTION + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MarkPower(owner, source, amount);
    }
}
