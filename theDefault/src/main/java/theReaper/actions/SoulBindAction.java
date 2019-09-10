package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.characters.TheDefault;
import theReaper.orbs.DefaultOrb;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.souls.LostSoul;

public class SoulBindAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SoulBindAction");
    private static final String[] DESCRIPTIONS = uiStrings.TEXT;
    private AbstractPlayer p;
    public static final Logger logger = LogManager.getLogger(SoulBindAction.class.getName());


    public SoulBindAction(final AbstractPlayer p, final AbstractMonster m,
                          final int amount) {
        setValues(m,p,amount);
        this.p = p;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.5F;


    }

    public SoulBindAction(final AbstractPlayer p, final AbstractMonster m) {
        this(p, m, AbstractPlayerSoulsPatch.soulBindAmount.get(p)); // use the current player's soulbind amount
    }

    @Override
    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;

            return;
        }

        if (this.duration == 0.5F) {

            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));

            if (this.target.currentHealth <= this.amount && !this.target.hasPower("Minion")) {

                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, DESCRIPTIONS[0], 4.0f, 2.0f)); // Player speech bubble saying "YOU ARE MINE!" (See relic strings)
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF")); // Sound Effect Action of The Collector Nails
                AbstractDungeon.actionManager.addToBottom(new VFXAction( // Visual Effect Action of the nails applies on a random monster's position.
                        new InflameEffect(this.target)));
                AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(this.target,p));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.target));

                AbstractDungeon.actionManager.addToBottom(new SoulGemAction(new LostSoul()));
            } else if (this.target.currentHealth <= this.amount && this.target.hasPower("Minion"))
            {
                // just kill the minion if we do enough damage.
                AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.target));

            }

        }

        tickDuration();

    }
}
