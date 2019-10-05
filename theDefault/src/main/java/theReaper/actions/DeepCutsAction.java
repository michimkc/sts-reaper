package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

public class DeepCutsAction extends AbstractGameAction {

    public AbstractCard card;

    //private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DeepCutsAction");
    //private static final String[] DESCRIPTIONS = uiStrings.TEXT;
    AbstractCreature m;
    float mult;

    public DeepCutsAction(AbstractCreature m, float multiplier) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.5F;
        this.m = m;
        this.mult = multiplier;
    }
    
    @Override
    public void update() {

        String bp = DefaultMod.makeID("BleedPower");

        if(m.hasPower(bp))
        {
            int stacksToAdd = (int)(m.getPower(bp).amount*mult);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,AbstractDungeon.player,new BleedPower(m,AbstractDungeon.player,stacksToAdd),stacksToAdd));
        }

        this.isDone = true;

        tickDuration();

    }
}
