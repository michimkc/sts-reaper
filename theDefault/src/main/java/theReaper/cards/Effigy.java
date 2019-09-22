package theReaper.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theReaper.DefaultMod;
import theReaper.actions.EffigyAction;
import theReaper.actions.SoulGemAction;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.souls.HollowSoul;
import theReaper.souls.LostSoul;

public class Effigy extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Effigy");
    public static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Effigy()
    {

        super(ID, COST, TYPE, RARITY, TARGET);

        this.exhaust = true;

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size() == 0) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(!upgraded) {
            AbstractDungeon.actionManager.addToBottom(new EffigyAction(p, false)); // only one soul
        } else
        {
            AbstractDungeon.actionManager.addToBottom(new EffigyAction(p, true)); // any number of souls
        }

    }


}
