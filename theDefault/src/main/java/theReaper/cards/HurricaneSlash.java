package theReaper.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theReaper.DefaultMod;
import theReaper.powers.RiteoftheBladePower;
import theReaper.util.SoulStrings;

import java.util.ArrayList;
import java.util.List;

public class HurricaneSlash extends AbstractCustomCard {


    public static final String ID = DefaultMod.makeID("HurricaneSlash");

    private static final SoulStrings soulString = DefaultMod.SoulStringsMap.get(ID);
    public static final String TIPSTITLE = soulString.NAME;
    public static final String[] TIPSDESCRIPTION = soulString.DESCRIPTIONS;

    public ArrayList<TooltipInfo> tips = new ArrayList<TooltipInfo>();

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    // Attacks hit all enemies.
    public HurricaneSlash()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 7;
        damageUp = 2;
        baseMagicNumber = magicNumber = 3;

        // 7 + 3*(numhit-1) + (numhit-2)[min 0]
        // 7 , 10 , 14 , 19 , 25
        // 9 , 12 , 16 , 21 , 27

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        MonsterGroup monsterGroup = AbstractDungeon.getCurrRoom().monsters;

        ArrayList<AbstractMonster> mList = new ArrayList<AbstractMonster>();

        for (int i = 0; i < monsterCount; i++) {
            mList.add(monsterGroup.monsters.get(i));
        }

        for (int i = 0; i < monsterCount; i++)
        {
            int calcDamage = damage;
            int counter = magicNumber;

            if (monsterCount > 1) {
                for (int j = 1; j < monsterCount; j++) {
                    calcDamage += counter;
                    counter++;
                }
            }


            int index = AbstractDungeon.aiRng.random(0, mList.size() - 1);
            AbstractMonster currentMonster = mList.get(index);

            if (currentMonster != null && currentMonster.hb != null) {
                act(new VFXAction(new ThrowDaggerEffect(currentMonster.hb.cX, currentMonster.hb.cY)));
            }
            act(new DamageAction(currentMonster, new DamageInfo(p, calcDamage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));

            mList.remove(index);
        }

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();

        String TIPSDESCRIPTIONCOMBINED = TIPSDESCRIPTION[0] +
                baseDamage + TIPSDESCRIPTION[1] +
                (baseDamage+3) + TIPSDESCRIPTION[1] +
                (baseDamage+4) + TIPSDESCRIPTION[1] +
                (baseDamage+5) + TIPSDESCRIPTION[1] +
                (baseDamage+6) + TIPSDESCRIPTION[1] +
                TIPSDESCRIPTION[2];

        this.tips.add(new TooltipInfo(TIPSTITLE,TIPSDESCRIPTIONCOMBINED));

        return this.tips;
    }
}
