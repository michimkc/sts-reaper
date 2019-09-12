package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractNonLethalCard extends AbstractCustomCard {


    public AbstractNonLethalCard(final String id, final int cost,
                             final CardType type, final CardRarity rarity, final CardTarget target)
    {

        super(id, cost, type, rarity, target);


    }

    public static void NonLethalDamageAction (AbstractMonster m, DamageInfo info, AbstractGameAction.AttackEffect effect)
    {
        NonLethalDamageAction(m, info, effect, true);
    }

    public static void NonLethalDamageAction (AbstractMonster m, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean killAtOneHP)
    {

        int damageFinal = info.base;
        if(info.type == DamageInfo.DamageType.HP_LOSS) // hp loss doesn't take into account block
        {

            if (m.currentHealth == 1 && info.base > 0) {
                if (!killAtOneHP) {
                    // don't kill it if we are going to soulbind or if we dont want this to kill
                    damageFinal = 0;
                } else {
                    // normally we let it kill
                    damageFinal = 1;
                }
            }
            else if (info.base >= m.currentHealth)
            {

                damageFinal = m.currentHealth - 1;
            }
        } else if (m.currentHealth + m.currentBlock == 1 && info.base > 0)
        {
            if(!killAtOneHP)
            {
                // don't kill it if we are going to soulbind
                damageFinal = 0;
            } else {
                damageFinal = 1;
            }

        } else if (info.base >= m.currentHealth + m.currentBlock)
        {

            damageFinal = m.currentHealth + m.currentBlock - 1;
        }

        DamageInfo finalInfo = new DamageInfo(AbstractDungeon.player,damageFinal,info.type);

        act(new DamageAction(m, finalInfo, effect));

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
