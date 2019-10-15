package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

public class Cyclone extends AbstractCustomCard {


    public static final String ID = DefaultMod.makeID("Cyclone");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    // Attacks hit all enemies.
    public Cyclone()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 7;
        damageUp = 3;
        isMultiDamage = true;

    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {

        if (c instanceof Cyclone){
            setCostForTurn(0);
        }

    }


    public void onCardDraw()
    {
        ArrayList<AbstractCard> cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        for(AbstractCard c : cardsPlayedThisTurn) {
            if (c instanceof Cyclone){
                setCostForTurn(0);
                break;
            }
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        AbstractCard cycloneCard = new Cyclone();
        if(upgraded)
        {
            cycloneCard.upgrade();
        }
        actionManager.addToBottom(new MakeTempCardInDrawPileAction(cycloneCard, 1, true, true));
        //actionManager.addToBottom(new MakeTempCardInDiscardAction(new Cyclone(), 1));

    }


}
