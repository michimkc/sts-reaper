package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.BleedLoseHpAction;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makePowerPath;

public class BleedPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public static final Logger logger = LogManager.getLogger(BleedPower.class.getName());

    public static final String POWER_ID = DefaultMod.makeID("BleedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bleed_placeholder84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bleed_placeholder32.png"));

    public BleedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;


        this.owner = owner;
        this.amount = amount;
        this.source = source;

        if (this.amount >= 9999) {
            this.amount = 9999;
        }

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    public void playApplyPowerSfx() { CardCrawlGame.sound.play("BLOOD_SWISH", 0.05F); }

    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            //AbstractDungeon.actionManager.addToBottom(new BleedLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
            this.amount += Math.max(1,this.amount*0.1F);
        }


    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {

        if(this.owner.hasPower(DefaultMod.makeID("LaceratePower")))
        {
            // do nothing. LaceratePower will increase the bleed stacks.
        } else if (damageAmount > 0){
            logger.info("Info type: " + info.type);
            if(info.type == DamageInfo.DamageType.NORMAL) {
                AbstractDungeon.actionManager.addToBottom(new BleedLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON, true));
            }
        }
        return damageAmount;
    }

    /*
    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new BleedLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
        }
    }
*/

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (this.owner == null || this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + this.amount*.1F + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount*.1F + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BleedPower(owner, source, amount);
    }
}
