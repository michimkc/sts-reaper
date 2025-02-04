package theReaper.cards;

import basemod.helpers.BaseModCardTags;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theReaper.DefaultMod;
import theReaper.util.SoulManager;

import java.util.List;

public class MaimingStrike extends AbstractNonLethalCard {

    public static final String ID = DefaultMod.makeID("MaimingStrike");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    public MaimingStrike()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 15;
        damageUp = 3;

        magicNumber = baseMagicNumber = 2;
        magicNumberUp = 1;

        this.tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        NonLethalDamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HEAVY);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }


    @Override
    // SOUL BIND TOOLTIP
    public List<TooltipInfo> getCustomTooltips() {
        return SoulManager.getCustomTooltips();
    }

}
