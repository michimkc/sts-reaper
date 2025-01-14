package theReaper.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import theReaper.DefaultMod;
import theReaper.actions.InsultToInjuryAction;

public class InsultToInjury extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("InsultToInjury");

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("InsultToInjuryDisses");
    private static final String[] TEXT = uiStrings.TEXT;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public InsultToInjury()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 2;
        damageUp = 1;
        this.retain = true;

    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new InsultToInjuryAction(p, m, damage, this));

    }


}
