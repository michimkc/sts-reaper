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
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.SoulManager;
import theReaper.util.SoulShopScreen;

public class RelicBaseButton extends AbstractSoulShopButton {
    public String name;
    public String description;
    public String ID;

    public static final Logger logger = LogManager.getLogger(RelicBaseButton.class.getName());


    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);


    public Hitbox hb;

    //public static final String imgURL = "theReaperResources/images/ui/runebutton-off.png";
    public boolean used = false;

    public AbstractRune rune;
    public boolean activated = false;

    public RelicBaseButton(AbstractRelicRune rune, SoulShopScreen scr) {
        super(rune, scr);
        //this.img = ImageMaster.loadImage(imgURL);
        this.ID = rune.getID();
        this.name = rune.getName();
        this.description = rune.getDescription();
        this.tX = 0;
        this.tY = 0;
        this.rune = rune;
        this.hb = new Hitbox(textureWidth * Settings.scale, textureHeight * Settings.scale);
        if(AbstractDungeon.player.hasRelic(rune.relic.relicId))
        {
            this.runeActivated = true;
        }
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void update() {
        this.hb.move(this.tX, this.tY);
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip((this.hb.x + 200.0F) * Settings.scale, (this.hb.y) * Settings.scale, this.name, this.description);

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

        Color tmpColor = Settings.SHADOW_COLOR;
        if(this.buttonEnabled)
        {
            tmpColor = Settings.HALF_TRANSPARENT_WHITE_COLOR;
        }

        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        if(this.rune != null)
        {
            if(this.rune instanceof AbstractRelicRune)
            {
                if(AbstractDungeon.player.hasRelic(DefaultMod.makeID(this.rune.name)))
                {
                    this.img = this.activatedImg;
                    tmpColor = Settings.GOLD_COLOR;
                }
            } else
            {
                this.img = this.unactivatedImg;
            }
            if(this.rune instanceof AbstractSoulShiftRune)
            {
                if(this.rune.name == DefaultMod.currentShiftRune.name)
                {
                    this.img = this.activatedImg;
                    tmpColor = Settings.GOLD_COLOR;
                } else
                {
                    this.img = this.unactivatedImg;
                }
            }
        }
        sb.draw(this.img, this.hb.x, this.hb.y, 0, 0, this.textureWidth, this.textureHeight, Settings.scale, Settings.scale, 0, 0, 0, (int)this.textureWidth, (int)this.textureHeight, false, false);


        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.name, this.tX, this.tY, tmpColor);
        this.hb.render(sb);
    }
}
