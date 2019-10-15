package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theReaper.DefaultMod;

public class DeathRattle extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("DeathRattle");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public DeathRattle()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 4;
        magicNumber2 = baseMagicNumber2 = 8;
        magicNumber2Up = -1;
        this.exhaust = true;
        magicNumber = baseMagicNumber = damage;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int orig = damage;
        damage = getCardDamage(baseDamage);
        initializeDescription();
        act(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL),DamageAction.AttackEffect.SLASH_HEAVY));
        damage = orig;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int orig = damage;
        damage = getCardDamage(baseDamage);
        if(damage != baseDamage) {
            isDamageModified = true;
        }
        initializeDescription();
        damage = orig;
    }

    public int getCardDamage(int d)
    {

        if(CardCrawlGame.isInARun()) {
            int cardDamage;
            int curHP = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
            int extraDamage = (int) (curHP / magicNumber2) * d;
            cardDamage = d + extraDamage;

            return cardDamage;
        }
        else
            return d;
    }


    public void update()
    {
        super.update();
        damage = getCardDamage(baseDamage);
        isDamageModified = true;
        initializeDescription();
    }

    public void upgrade()
    {
        super.upgrade();
    }

}
