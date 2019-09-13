package theReaper.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theReaper.DefaultMod;
import theReaper.powers.DeathsDancePower;
import theReaper.powers.RiteoftheBladePower;

import java.util.ArrayList;

public class DeathsDance extends AbstractCustomCard {

    public static final String ID = DefaultMod.makeID("DeathsDance");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    // Attacks hit all enemies.
    public DeathsDance()
    {

        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        newCost = 1;
        //this.tags.add(BaseModCardTags.FORM); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33F));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.33F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new VerticalAuraEffect(Color.RED, p.hb.cX, p.hb.cY), 0.0F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderLongFlashEffect(Color.PURPLE), 0.0F, true));

        boolean powerExists = false;
        for (AbstractPower pow : p.powers) {
            if (pow.ID.equals("DeathsDancePower")) {
                powerExists = true;

                break;
            }
        }
        if (!powerExists) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DeathsDancePower(p)));
        }
    }


}
