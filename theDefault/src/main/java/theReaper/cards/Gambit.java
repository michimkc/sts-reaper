package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;
import theReaper.powers.BleedPower;

public class Gambit extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("Gambit");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 4;

    public Gambit() {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 18;
        damageUp = 6;

    }


    public void onCardDraw()
    {
        setCostForTurn(this.cost -1);
    }

    public void triggerWhenDrawn() {
        // reduce the cost by the number of cards we drew t his turn
        setCostForTurn(this.cost - DefaultMod.cardsDrawnThisTurn + AbstractDungeon.player.gameHandSize);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }


}