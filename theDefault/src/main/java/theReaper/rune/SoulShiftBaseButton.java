package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import theReaper.util.SoulShopScreen;

public class SoulShiftBaseButton extends AbstractSoulShopButton {
    public String name;
    public String description;
    public String ID;

    public static final Logger logger = LogManager.getLogger(SoulShiftBaseButton.class.getName());


    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 299F;
    public static float textureHeight = 156F;

    public Hitbox hb = new Hitbox(textureWidth * Settings.scale, textureHeight * Settings.scale);

    public static final String imgURL = "theReaperResources/images/ui/greyrunebutton.png";
    public boolean used = false;

    public AbstractSoulShiftRune rune;

    public SoulShiftBaseButton(AbstractSoulShiftRune rune, SoulShopScreen scr) {
        super(rune, scr);
        this.img = ImageMaster.loadImage(imgURL);
        this.ID = rune.getID();
        this.name = rune.getName();
        this.description = rune.getDescription();
        this.tX = 0;
        this.tY = 0;
        this.rune = rune;
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void update() {
        this.hb.move(this.tX, this.tY);
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip((this.tX + 175.0F) * Settings.scale, (this.tY + 50.0F) * Settings.scale, this.name, this.description);

            if (InputHelper.justClickedLeft) {
                InputHelper.justClickedLeft = false;
                this.hb.clickStarted = true;
            }
        }
        if (!runeActivated && buttonEnabled) {
            if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.hb.clicked = false;

                onUse();
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        sb.draw(this.img, this.hb.x, this.hb.y, 0, 0, this.textureWidth, this.textureHeight, Settings.scale, Settings.scale, 0, 0, 0, (int)this.textureWidth, (int)this.textureHeight, false, false);

        Color tmpColor = Settings.CREAM_COLOR;
        if(this.buttonEnabled)
        {
            tmpColor = Settings.GOLD_COLOR;
        }
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.name, this.tX, this.tY, tmpColor);
        this.hb.render(sb);
    }
}
