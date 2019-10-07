package theReaper.rune;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.patches.AbstractDungeonScreenPatch;
import theReaper.patches.ReaperEnums;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulManager;
import theReaper.util.SoulSelectScreen;
import theReaper.util.SoulShopScreen;

public class RunePageMenuButton {

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

    public float runeAngle = 0.0F;
    public float TIP_Y = Settings.HEIGHT - 120.0F * Settings.scale;
    public float TIP_OFF_X = 1550.0F * Settings.scale;

    public boolean ButtonDisabled = false;
    private static final Color DISABLED_BTN_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);

    public RunePageMenuButton() {
        this.img = ImageMaster.loadImage(imgURL);

        float ICON_W = 64.0F * Settings.scale;
        float TOP_RIGHT_PAD_X = 10.0F * Settings.scale;
        float ICON_Y = (Settings.HEIGHT ) - ICON_W;

        this.tX = (Settings.WIDTH) - (ICON_W + TOP_RIGHT_PAD_X)*4.0F + 32.0F * Settings.scale;
        this.tY = ICON_Y + 32.0F * Settings.scale;
        //this.tX =(150+(20.0f*Settings.scale+ICON_W));

        //reaperBaseString = DefaultMod.ReaperStringsMap.get(DefaultMod.makeID("SoulAction"));
        //BaseSoulDescription = reaperBaseString.DESCRIPTIONS;
        //BaseSoulTipName = reaperBaseString.NAME;
    }

    public void onClick()
    {
       //open menu
        logger.info("Clicked the button. Open the menu then.");
        if(DefaultMod.currentRune instanceof SoulShiftDrawRune)
        {
            SoulManager.soulShift(new SoulShiftEnergyRune(3));
        } else
        {
            SoulManager.soulShift(new SoulShiftDrawRune(2));
        }
    }

    public void update() {
        updateButtonLogic();
        this.hb.move(this.tX, this.tY);
        this.hb.update();
        if(this.hb.justHovered)
        {
            CardCrawlGame.sound.play("UI_HOVER");
        }
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(TIP_OFF_X, TIP_Y, this.name, this.description);
           // TipHelper.renderGenericTip((this.tX + 175.0F) * Settings.scale, (this.tY - 100.0F) * Settings.scale, this.BaseSoulTipName, this.BaseSoulDescription[0]);

            if (InputHelper.justClickedLeft) {

                this.hb.clickStarted = true;
                for (AbstractGameEffect e : AbstractDungeon.topLevelEffects) {
                    if (e instanceof com.megacrit.cardcrawl.vfx.FadeWipeParticle) {
                        return;
                    }
                }

                SoulShopScreen scr = AbstractDungeonScreenPatch.soulShopScreen.get(CardCrawlGame.dungeon);

                if (AbstractDungeon.screen == ReaperEnums.SOULSHOPSCREEN) {
                    CardCrawlGame.sound.play("CARD_REJECT"); // close the screen if it's open.
                } else if (!AbstractDungeon.isScreenUp) {
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                    AbstractDungeon.closeCurrentScreen();
                    scr.open();
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.BOSS_REWARD;
                    AbstractDungeon.bossRelicScreen.hide();
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.MAP;
                    scr.open();
                } else if (AbstractDungeon.screen == ReaperEnums.SOULSHOPSCREEN) {
                    AbstractDungeon.screenSwap = false;
                    if (AbstractDungeon.previousScreen == ReaperEnums.SOULSHOPSCREEN) {
                        AbstractDungeon.previousScreen = null;
                    }
                    AbstractDungeon.closeCurrentScreen();
                    CardCrawlGame.sound.play("MAP_CLOSE", 0.05F);

                    if ((AbstractDungeon.getCurrRoom()).rewardTime) {
                        AbstractDungeon.overlayMenu.cancelButton.hide();
                        AbstractDungeon.combatRewardScreen.reopen();
                    }
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DEATH) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
                    AbstractDungeon.deathScreen.hide();
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW) {

                        if (AbstractDungeon.previousScreen != null) {
                            AbstractDungeon.screenSwap = true;
                        }
                        AbstractDungeon.closeCurrentScreen();
                        scr.open();

                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.INPUT_SETTINGS) {

                        if (AbstractDungeon.previousScreen != null) {
                            AbstractDungeon.screenSwap = true;
                        }
                        AbstractDungeon.closeCurrentScreen();
                        scr.open();

                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.CARD_REWARD;
                    AbstractDungeon.dynamicBanner.hide();
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.GRID;
                    AbstractDungeon.gridSelectScreen.hide();
                    scr.open();
                } else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) {
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.HAND_SELECT;
                    scr.open();
                }

                InputHelper.justClickedLeft = false;

            }
        }
        if(!used) {
            if (this.hb.clicked || CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.hb.clicked = false;

                //stuff happens here
                if(inLevelUpScreen)
                {
                    if(currentSelectScreen == null)
                    {
                        logger.info("currentSelectScreen not set. Cancelling select screen mode for this soul.");
                        inLevelUpScreen = false;
                    } else
                    {
                        // selectScreen is set.
                        logger.info("Menu is opened and we clicked the button. Close?");
                    }

                } else {
                    onClick();
                }
            }
        }
    }

    public void updateButtonLogic()
    {

        if (this.hb.hovered) {
            this.runeAngle = MathHelper.angleLerpSnap(this.runeAngle, 20.0F);

        } else
        {
            this.runeAngle = MathHelper.angleLerpSnap(this.runeAngle, 0.0F);
        }

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.INPUT_SETTINGS || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DEATH || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.VICTORY || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
            this.ButtonDisabled = false;
        } else {
            this.ButtonDisabled = true;
            this.hb.hovered = false;
        }
    }

    public void render(SpriteBatch paramSpriteBatch) {
        if (this.ButtonDisabled) {
            paramSpriteBatch.setColor(DISABLED_BTN_COLOR);
        } else {
            paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 1.0f));
        }
        paramSpriteBatch.draw(this.img, this.hb.x, this.hb.y, 32.0F, 32.0F, textureWidth, textureWidth, 1, 1, this.runeAngle, 0, 0, 300, 300, false, false);

        //paramSpriteBatch.draw(this.img,Settings.WIDTH/2, Settings.HEIGHT/2);
        hb.render(paramSpriteBatch);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

}
