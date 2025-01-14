package theReaper.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.powers.CommonPower;
import theReaper.powers.DeepCutsPower;
import theReaper.powers.MarkPower;
import theReaper.relics.AnemoneRelic;
import theReaper.relics.ClawsRelic;
import theReaper.relics.FangsRelic;

public class BleedLoseHpAction extends AbstractGameAction {

    private static final float PERCENTREDUCTION = 0.5f;
    public boolean consumePower = true; // do we consume the power upon dealing damage?
    public boolean nonLethal = true; // is the damage nonLethal?

    private static final Logger logger = LogManager.getLogger(BleedLoseHpAction.class.getName());

    public BleedLoseHpAction(final AbstractCreature target, final AbstractCreature source,
                             final int amount, AbstractGameAction.AttackEffect effect, boolean consumePower, boolean nonLethal) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
        this.consumePower = consumePower;
        this.nonLethal = nonLethal;
    }

    
    @Override
    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;

            return;
        }
        if (this.duration == 0.33F && this.target.currentHealth > 0) {

            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }


        tickDuration();

        if (this.isDone) {
            int finalAmount = 0;
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                finalAmount = this.amount;
                if(this.amount >= this.target.currentHealth && this.nonLethal)
                {
                    finalAmount = this.target.currentHealth - 1;
                }

                this.target.damage(new DamageInfo(this.source, finalAmount, DamageInfo.DamageType.HP_LOSS));


            }

            AbstractPower p = this.target.getPower("theReaper:BleedPower");
            if (p != null) {

                if (this.target.hasPower(DefaultMod.makeID(DeepCutsPower.POWER_NAME)))
                {
                    // deep cuts makes it so that the bleed doesn't reduce.
                    this.target.getPower(DefaultMod.makeID(DeepCutsPower.POWER_NAME)).flash();
                }
                //else if (bleed <= 1) {
                //    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.target, this.target,p));
                //}
                else if(this.consumePower == true) {
                    //int percentAmount = Math.round(bleed * PERCENTREDUCTION);
                    //int amountToReduce = percentAmount;
                    //p.amount -= amountToReduce;
                    //if (bleed <= 1) {

                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.target, this.target,p));

                        if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(FangsRelic.name)) && !this.target.isPlayer)
                        {
                            AbstractDungeon.actionManager.addToBottom(new ConsumeMarksAction(this.target,finalAmount));
                            AbstractDungeon.player.getRelic(DefaultMod.makeID(FangsRelic.name)).flash();
                            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,
                                    AbstractDungeon.player.getRelic(DefaultMod.makeID(FangsRelic.name))));
                        }

                        if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(AnemoneRelic.name)) && !this.target.isPlayer)
                        {

                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, AbstractDungeon.player, new WeakPower(this.target, AnemoneRelic.weakAmount, false), AnemoneRelic.weakAmount));

                            AbstractDungeon.player.getRelic(DefaultMod.makeID(AnemoneRelic.name)).flash();
                            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,
                                    AbstractDungeon.player.getRelic(DefaultMod.makeID(AnemoneRelic.name))));
                        }

                        if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(ClawsRelic.name)) && this.target.isPlayer)
                        {
                            logger.info("Has Claws relic, applying " + finalAmount + " marks to " + this.source);
                            MarkPower.applyMarks(this.source,this.target,finalAmount);
                            AbstractDungeon.player.getRelic(DefaultMod.makeID(ClawsRelic.name)).flash();
                            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,
                                    AbstractDungeon.player.getRelic(DefaultMod.makeID(ClawsRelic.name))));
                        }
                    //}

                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        }
    }
}
