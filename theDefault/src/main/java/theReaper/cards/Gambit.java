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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.patches.AbstractPlayerSoulsPatch;
import theReaper.powers.BleedPower;

public class Gambit extends AbstractCustomCard {


    private static final Logger logger = LogManager.getLogger(Gambit.class.getName());

    public static final String ID = DefaultMod.makeID("Gambit");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 4;

    private int cardsDrawn = 0;

    public Gambit() {

        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 18;
        damageUp = 6;
        cardsDrawn = 0;
    }

    public void onDiscard()
    {

    }

    public void onCardDraw()
    {
        cardsDrawn = -DefaultMod.cardsDrawnThisTurn + AbstractDungeon.player.gameHandSize;
        setCostForTurn(this.cost + cardsDrawn);

        logger.info("onCardDraw. Cards drawn this turn: " + DefaultMod.cardsDrawnThisTurn + " , Cards Drawn: " + cardsDrawn + " , current Cost is : " + this.cost);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }


}