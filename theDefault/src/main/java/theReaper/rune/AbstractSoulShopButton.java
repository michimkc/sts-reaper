package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.patches.AbstractDungeonScreenPatch;
import theReaper.util.SoulManager;
import theReaper.util.SoulShopScreen;

public abstract class AbstractSoulShopButton {
    public String name;
    public String description;
    public String ID;

    public static final Logger logger = LogManager.getLogger(AbstractSoulShopButton.class.getName());

    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 227F;
    public static float textureHeight = 80F;

    public Hitbox hb;

    public static final String imgURL = "theReaperResources/images/ui/runebutton-off.png";
    public static final String activatedImgURL = "theReaperResources/images/ui/runebutton-on.png";
    public boolean runeActivated = false;

    public Texture activatedImg;
    public Texture unactivatedImg;

    public AbstractRune rune;
    public boolean buttonEnabled = false;
    public SoulShopScreen parentScreen;

    public AbstractSoulShopButton(AbstractRune rune, SoulShopScreen scr) {
        this.unactivatedImg = ImageMaster.loadImage(imgURL);
        this.activatedImg = ImageMaster.loadImage(activatedImgURL);
        this.img = unactivatedImg;
        this.ID = rune.getID();
        this.name = rune.getName();
        this.description = rune.getDescription();
        this.tX = 0;
        this.tY = 0;
        this.rune = rune;
        this.hb = new Hitbox(textureWidth * Settings.scale, textureHeight * Settings.scale);
        this.parentScreen = scr;
    }


    public void onUse() {

        if (this.rune instanceof AbstractSoulShiftRune) {
            SoulManager.soulShift((AbstractSoulShiftRune) this.rune);

            parentScreen.activateAllSoulShiftButtons(false);
            parentScreen.enableAllSoulShiftButtons(true);
            this.buttonEnabled = false;
            this.runeActivated = true;

        } else if (this.rune instanceof AbstractRelicRune) {
            this.rune.onUse();
            this.buttonEnabled = false;
            this.runeActivated = true;
        }
    }

    public abstract void update();

    public void render(SpriteBatch sb) {
        Color tmpColor = Settings.SHADOW_COLOR;
        if (this.buttonEnabled) {
            tmpColor = Settings.HALF_TRANSPARENT_WHITE_COLOR;
        }

        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        if (this.rune != null) {
            if (this.rune.relic != null) {
                if (AbstractDungeon.player.hasRelic(rune.relic.relicId)) {
                    this.img = this.activatedImg;
                    tmpColor = Settings.GOLD_COLOR;
                }
            } else {
                this.img = this.unactivatedImg;
            }

            if (this.rune instanceof AbstractSoulShiftRune) {
                if (this.rune.name == DefaultMod.currentShiftRune.name) {
                    this.img = this.activatedImg;
                    tmpColor = Settings.GOLD_COLOR;
                } else {
                    this.img = this.unactivatedImg;
                }
            }
        }

        sb.draw(this.img, this.hb.x, this.hb.y, 0, 0, this.textureWidth, this.textureHeight, Settings.scale, Settings.scale, 0, 0, 0, (int) this.textureWidth, (int) this.textureHeight, false, false);


        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.name, this.tX, this.tY, tmpColor);
        this.hb.render(sb);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
