package theReaper.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.powers.UndyingPower;

public class FadedLocketRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "FadedLocketRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(FadedLocketRelic.class.getName());

    public boolean used = false;
    public static int bonusUndying = 1;
    public static int percentHealth = 25;

    public FadedLocketRelic() {
        super(name);
    }

    @Override
    public void atBattleStart() {

        if(AbstractDungeon.player.currentHealth <= healthThreshold() && this.used == false)
        {
            applyUndying();
        } else
        {
            this.used = false;
            this.beginLongPulse();
        }
    }

    @Override
    public void onEquip() {
        atBattleStart();
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if(!this.used)
        {
            if(AbstractDungeon.player.currentHealth - damageAmount <= healthThreshold())
            {
                applyUndying();
            }

        }
    }

    public int healthThreshold()
    {
        return AbstractDungeon.player.maxHealth * percentHealth/100;
    }

    public void applyUndying()
    {
        this.used = true;
        this.stopPulse();
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,
                this));

        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new UndyingPower(p, p, bonusUndying), bonusUndying));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + percentHealth + DESCRIPTIONS[1] + bonusUndying + DESCRIPTIONS[2];
    }

}
