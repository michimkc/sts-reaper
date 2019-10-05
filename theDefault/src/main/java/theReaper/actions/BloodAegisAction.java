package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theReaper.DefaultMod;

import java.util.ArrayList;

public class BloodAegisAction extends AbstractGameAction {

    public AbstractCard card;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BloodAegisAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;

    public BloodAegisAction() {
        this.actionType = ActionType.BLOCK;
        this.duration = 0.5F;

    }
    
    @Override
    public void update() {
        int totalBlock = 0;
        AbstractPlayer p = AbstractDungeon.player;
        String bp = DefaultMod.makeID("BleedPower");

        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if(m.hasPower(bp))
            {
                totalBlock += m.getPower(bp).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, bp));
            }
        }

        if(p.hasPower(bp))
        {
            if(p.hasPower(bp))
            {
                totalBlock += p.getPower(bp).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, bp));
            }
        }

        if(totalBlock > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, totalBlock));
        } else
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTIONS[0], true));
        }

        this.isDone = true;

        tickDuration();

    }
}
