package theReaper.cards;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractNonLethalCard extends AbstractCustomCard {


    public AbstractNonLethalCard(final String id, final int cost,
                             final CardType type, final CardRarity rarity, final CardTarget target)
    {

        super(id, cost, type, rarity, target);


    }

    /*
    This modified calculateCardDamage is for Non-Lethal damage
    Non-Lethal damage will deal 1 damage if the enemy is at 1hp.
     */
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        if (mo.currentHealth + mo.currentBlock == 1 && damage > 0)
        {
            damage = 1;
            this.isDamageModified = true;
        } else if (damage >= mo.currentHealth + mo.currentBlock)
        {
            damage = mo.currentHealth + mo.currentBlock - 1;
            this.isDamageModified = true;
        }

    }


}
