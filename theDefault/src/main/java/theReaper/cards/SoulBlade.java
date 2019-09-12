package theReaper.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theReaper.DefaultMod;
import theReaper.patches.AbstractPlayerSoulsPatch;

public class SoulBlade extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("SoulBlade");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private int actualBaseDamage = 5;

    public SoulBlade()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = actualBaseDamage;
        baseMagicNumber = magicNumber = 3;
        magicNumberUp = 2;

        baseMagicNumber2 = magicNumber2 = actualBaseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        float vfxSpeed = 0.1F;

        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }


        if(damage > baseDamage) {
            act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX - 60.0F * Settings.scale, m.hb.cY, false), vfxSpeed));
        } else {
            act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }


    }

    public void onSoulCountChanged() {
        initializeDescription();
    }

    public void applyPowers() {
        baseDamage = totalDamage();
        super.applyPowers();

        isDamageModified = true;
        baseDamage = actualBaseDamage;
    }

    public void calculateCardDamage(AbstractMonster m) {
        baseDamage = totalDamage();
        super.calculateCardDamage(m);

        isDamageModified = true;
        baseDamage = actualBaseDamage;

    }

    public int totalDamage()
    {

        return actualBaseDamage + magicNumber*AbstractPlayerSoulsPatch.souls.get(AbstractDungeon.player).size();

    }
}
