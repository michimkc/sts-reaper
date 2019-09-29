package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulSelectScreen;

public abstract class RunePageMenuButton {

    public String name = "Runes";
    public String description = "Make this a ui strings";
    public String ID = DefaultMod.makeID("RuneMenuButton");

    public static final Logger logger = LogManager.getLogger(RunePageMenuButton.class.getName());

    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 64.0F;

    private static ReaperStrings reaperBaseString;
    public static String[] BaseSoulDescription;
    public static String BaseSoulTipName;

    public Hitbox hb = new Hitbox(textureWidth * Settings.scale, textureWidth * Settings.scale);

    public boolean used = false;
    public boolean inLevelUpScreen = false;
    public SoulSelectScreen currentSelectScreen;
    public static final String imgURL = "theReaperResources/images/ui/menu-runemenuicon.png";

    public RunePageMenuButton() {
        this.img = ImageMaster.loadImage(imgURL);

        float ICON_W = 64.0F * Settings.scale;
        float TOP_RIGHT_PAD_X = 10.0F * Settings.scale;
        float ICON_Y = Settings.HEIGHT - ICON_W;

        this.tX = Settings.WIDTH - (ICON_W + TOP_RIGHT_PAD_X) * 1.0F;
        this.tY = ICON_Y;

        //reaperBaseString = DefaultMod.ReaperStringsMap.get(DefaultMod.makeID("SoulAction"));
        //BaseSoulDescription = reaperBaseString.DESCRIPTIONS;
        //BaseSoulTipName = reaperBaseString.NAME;
    }

    public abstract void updateDescription();

    public void onClick()
    {
       //open menu
    }

    public abstract RunePageMenuButton makeCopy();


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
        if(!used) {
            if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.hb.clicked = false;

                //stuff happens here
                if(inLevelUpScreen)
                {
                    // we were pressed but the mode is inSelectionScreen.
                    if(currentSelectScreen == null)
                    {
                        logger.info("currentSelectScreen not set. Cancelling select screen mode for this soul.");
                        inLevelUpScreen = false;
                    } else
                    {
                        // selectScreen is set.
                    }

                } else {
                    onClick();
                }
            }
        }
    }


    public void render(SpriteBatch paramSpriteBatch) {
        paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 1.0f));
        paramSpriteBatch.draw(this.img, this.hb.x, this.hb.y, 0, 0, textureWidth, textureWidth, Settings.scale, Settings.scale, 0, 0, 0, 300, 300, false, false);

        //paramSpriteBatch.draw(this.img,Settings.WIDTH/2, Settings.HEIGHT/2);
        hb.render(paramSpriteBatch);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
