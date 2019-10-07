package theReaper.souls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
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
import theReaper.rune.SoulShiftDrawRune;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulManager;
import theReaper.util.SoulSelectScreen;

public class SoulTip {

    public static String ID = DefaultMod.makeID("SoulTip");

    private static ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(ID);
    public static String name = reaperString.NAME;

    public String currentRuneName;
    public String currentRuneDesc;

    public static final Logger logger = LogManager.getLogger(SoulTip.class.getName());

    public float tX;
    public float tY;
    public static float textureWidth = 150F;

    private static final float SHADOW_DIST_Y = 14.0F * Settings.scale;
    private static final float SHADOW_DIST_X = 9.0F * Settings.scale;
    private static final float BOX_EDGE_H = 32.0F * Settings.scale;
    private static final float BOX_BODY_H = 64.0F * Settings.scale;
    private static final float BOX_W = 320.0F * Settings.scale;
    private static final float TEXT_OFFSET_X = 22.0F * Settings.scale;
    private static final float HEADER_OFFSET_Y = 12.0F * Settings.scale;
    private static final float BODY_OFFSET_Y = -20.0F * Settings.scale;
    private static final float BODY_TEXT_WIDTH = 280.0F * Settings.scale;
    private static final float TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    private static final Color BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
    private static String BODY = null;

    private static float textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, BODY, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
    public SoulTip() {

        this.tX = 0;
        this.tY = Settings.HEIGHT/2.0F + 300.0F * Settings.scale;

        LoadSoulShiftRune();
    }

    public void LoadSoulShiftRune()
    {
        if(DefaultMod.currentRune == null)
        {
            DefaultMod.currentRune = new SoulShiftDrawRune(2);
            currentRuneName = DefaultMod.currentRune.getName();
            currentRuneDesc = DefaultMod.currentRune.getDescription();
        } else
        {
            currentRuneName = DefaultMod.currentRune.getName();
            currentRuneDesc = DefaultMod.currentRune.getDescription();
        }
    }

    public void render(SpriteBatch paramSpriteBatch) {
        //paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        //paramSpriteBatch.draw(this.img, this.hb.x, this.hb.y, 0, 0, textureWidth, textureWidth, Settings.scale, Settings.scale, 0, 0, 0, 200, 200, false, false);
        renderTipBox((this.tX), (this.tY) , paramSpriteBatch, this.name + this.currentRuneName, this.currentRuneDesc);

        //paramSpriteBatch.draw(this.img,Settings.WIDTH/2, Settings.HEIGHT/2);
        //hb.render(paramSpriteBatch);
    }


    public static void renderTipBox(float x, float y, SpriteBatch sb, String title, String description) {
        float h = textHeight;

        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);

        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);

        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }


}
