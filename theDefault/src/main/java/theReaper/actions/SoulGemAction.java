package theReaper.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.souls.AbstractSoul;
import theReaper.souls.LostSoul;
import theReaper.util.SoulManager;

public class SoulGemAction extends AbstractGameAction {

    private AbstractSoul soul;
    public static final Logger logger = LogManager.getLogger(SoulGemAction.class.getName());
    public float durationDefault = 0.5f;

    public SoulGemAction(AbstractSoul pSoul) {

        this.soul = pSoul;
        this.duration = durationDefault;

    }

    @Override
    public void update() {

        if (this.duration == durationDefault) {

            logger.info("Adding Soul: " + this.soul.name);
            SoulManager.addSoul(this.soul);

        }

        tickDuration();

    }
}
