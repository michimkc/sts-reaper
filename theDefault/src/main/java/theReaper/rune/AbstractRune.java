package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.AbstractSoulOnAfterUseAction;
import theReaper.cards.AbstractCustomCard;
import theReaper.powers.AbstractCustomPower;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulManager;
import theReaper.util.SoulSelectScreen;

public abstract class AbstractRune {
    public String name;
    public String description;
    public String ID;

    public static final Logger logger = LogManager.getLogger(AbstractRune.class.getName());

    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 150F;

    private static ReaperStrings reaperBaseString;
    public static String[] BaseSoulDescription;
    public static String BaseSoulTipName;

    public Hitbox hb = new Hitbox(textureWidth * Settings.scale, textureWidth * Settings.scale);

    public AbstractRune() {

        this.tX = 0;
        this.tY = 0;

    }

    public void onUse()
    {

    }

    public abstract String getID();

    public abstract String getName();

    public abstract String getDescription();

    public abstract AbstractRune makeCopy();


    public void update() {
        this.hb.move(this.tX, this.tY);
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip((this.tX + 175.0F) * Settings.scale, (this.tY + 100.0F) * Settings.scale, this.name, this.description);
           // TipHelper.renderGenericTip((this.tX + 175.0F) * Settings.scale, (this.tY - 100.0F) * Settings.scale, this.BaseSoulTipName, this.BaseSoulDescription[0]);

            if (InputHelper.justClickedLeft) {
                InputHelper.justClickedLeft = false;
                this.hb.clickStarted = true;
            }
        }

        if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
            CInputActionSet.select.unpress();
            this.hb.clicked = false;

                onUse();
        }

    }


    public void render(SpriteBatch paramSpriteBatch) {
        paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        paramSpriteBatch.draw(this.img, this.hb.x, this.hb.y, 0, 0, textureWidth, textureWidth, Settings.scale, Settings.scale, 0, 0, 0, 200, 200, false, false);

        hb.render(paramSpriteBatch);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
