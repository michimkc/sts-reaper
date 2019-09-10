package theReaper.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.TextureLoader;

import static theReaper.DefaultMod.makePowerPath;

//At end of enemy turn, returns all damage that enemies dealt to player.

public abstract class AbstractCustomPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public String[] DESCRIPTIONS;

    public AbstractCustomPower(final AbstractCreature owner, final AbstractCreature source, String POWERNAME, PowerType type, boolean isTurnBased) {

        ID = DefaultMod.makeID(POWERNAME);
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;

        this.isTurnBased = isTurnBased;

        // We load those textures here.
        Texture tex84 = TextureLoader.getTexture(makePowerPath(POWERNAME + "84.png"));
        Texture tex32 = TextureLoader.getTexture(makePowerPath(POWERNAME + "32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onMonsterDeath(AbstractMonster m) {}

}
