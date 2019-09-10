package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

import java.util.ArrayList;

import static theReaper.DefaultMod.makePowerPath;

//At end of enemy turn, returns all damage that enemies dealt to player.

public class PaybackPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public static final Logger logger = LogManager.getLogger(PaybackPower.class.getName());

    public static final String POWER_ID = DefaultMod.makeID("PaybackPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("payback_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("payback_power32.png"));

    private ArrayList<DamageAction> damageList;

    public PaybackPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.damageList = new ArrayList<DamageAction>();

        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {

        if(info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) // if we were hit by something
        {

            flash();
            // Add marks to the enemy.
            damageList.add(new DamageAction(info.owner, new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        }

        return damageAmount;
    }

    public void atEndOfRound() {
        if(!damageList.isEmpty())
        {
            damageList.forEach((n) -> AbstractDungeon.actionManager.addToBottom(n));
        }
    }

    public void atStartOfTurn() {

        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PaybackPower(owner, source, amount);
    }
}
