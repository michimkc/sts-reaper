package theReaper.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theReaper.powers.CommonPower;

public class BleedLoseHpAction extends AbstractGameAction {

    public BleedLoseHpAction(final AbstractCreature target, final AbstractCreature source,
                             final int amount, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
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
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                int finalAmount = this.amount;
                if(this.amount >= this.target.currentHealth)
                {
                    finalAmount = this.target.currentHealth - 1;
                }

                this.target.damage(new DamageInfo(this.source, finalAmount, DamageInfo.DamageType.HP_LOSS));


            }

            AbstractPower p = this.target.getPower("theReaper:BleedPower");
            if (p != null) {
                p.amount--;
                if (p.amount == 0) {
                    this.target.powers.remove(p);
                } else {
                    p.updateDescription();
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        }
    }
}
