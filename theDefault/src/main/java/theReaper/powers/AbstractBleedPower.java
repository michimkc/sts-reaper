package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makePowerPath;

public abstract class AbstractBleedPower extends AbstractCustomPower {

        public AbstractBleedPower(final AbstractCreature owner, final AbstractCreature source,int amount, String POWERNAME, PowerType type, boolean isTurnBased) {
            super(owner,source,amount,POWERNAME,type,isTurnBased);
        }

        public abstract int increaseBleed(int bleedAmount);
    }
