package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
        damage = baseDamage = 5;
        magicNumber = baseMagicNumber = 4;
        magicNumber2 = baseMagicNumber2 = 8;
        magicNumber2Up = -1;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int curHP = p.maxHealth - p.currentHealth;
        int extraDamage = (int)(curHP/magicNumber2)*magicNumber;
        damage = baseDamage + extraDamage;
        act(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL),DamageAction.AttackEffect.SLASH_HEAVY));

    }

    public void update()
    {
        super.update();

        int curHP = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        int extraDamage = (int)(curHP/magicNumber2)*magicNumber;
        damage = baseDamage + extraDamage;
        isDamageModified = true;

        initializeDescription();
    }

    public void upgrade()
    {
        super.upgrade();
    }

}
