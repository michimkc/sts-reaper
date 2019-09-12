package theReaper.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theReaper.DefaultMod;
import theReaper.actions.SoulGemAction;
import theReaper.souls.LostSoul;
import com.badlogic.gdx.graphics.Color;

public class Glowfly extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Glowfly");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Glowfly()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        newCost = 0;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SoulGemAction(new LostSoul()));
        CardCrawlGame.sound.play("POWER_DEXTERITY", 0.05F);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new VerticalAuraEffect(Color.GOLD, p.hb.cX, p.hb.cY), 0.0F));
    }


}
