package theReaper.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
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

    // The action for this relic is checked in CardGroup, patched with CardGroupOldCharmPatch

    @Override
    public void atBattleStart()
    {
        usedThisCombat = false;
        this.pulse = true;
        beginPulse();
    }

    public void onVictory() { this.pulse = false; }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
