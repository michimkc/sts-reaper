package theReaper.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.actions.RaiseMaxHpAction;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makeRelicOutlinePath;
import static theReaper.DefaultMod.makeRelicPath;

public class OldCharmRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * When you kill an enemy, gain 1 hp
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("OldCharmRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("oldcharm_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("oldcharm_relic.png"));

    public boolean usedThisCombat = false;

    public OldCharmRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public final int HpRaiseAmount = 1;

    @Override
    public void atBattleStart()
    {
        usedThisCombat = false;
        this.pulse = true;
        beginPulse();
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {

        if(!m.hasPower("Minion") && !usedThisCombat) {
            AbstractDungeon.actionManager.addToBottom(new RaiseMaxHpAction(AbstractDungeon.player, HpRaiseAmount));
            usedThisCombat = true;
            this.pulse = false;
            flash();
        }
    }

    public void onVictory() { this.pulse = false; }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HpRaiseAmount + DESCRIPTIONS[1];
    }

}
