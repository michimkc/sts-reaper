package theReaper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.ui.buttons.CardSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.CustomGameAction;
import theReaper.patches.AbstractDungeonScreenPatch;
import theReaper.patches.ReaperEnums;
import theReaper.rune.*;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;


public class SoulShopScreen {

    public static final Logger logger = LogManager.getLogger(SoulShopScreen.class.getName());
    public static final String className = "SoulShopScreen";
    public static final String CLASS_ID = DefaultMod.makeID(className);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(CLASS_ID);
    //public static final String[] DESC = reaperString.DESCRIPTIONS;

    public String message = "";


    public ConfirmButton confirmButton = new ConfirmButton();

    public boolean waitThenClose = false;
    public float waitToCloseTimer = 0.0F;

    public ArrayList<SoulShiftBaseButton> soulShiftButtonList;
    public ArrayList<RelicBaseButton> relicButtonList;


    public static float defaultHeight = Settings.HEIGHT - (300.0F * Settings.scale);
    public static float heightSpacer =( SoulShiftBaseButton.textureHeight + 20F )* Settings.scale;
    public static float defaultWidth = 150*Settings.scale;
    public static float widthSpacer = (SoulShiftBaseButton.textureWidth + 20F ) * Settings.scale;

    public SoulShopScreen()
    {
        logger.info("making soul shop screen");
        soulShiftButtonList = new ArrayList<>();
        makeSoulShiftButtons();
        relicButtonList = new ArrayList<>();
        makeRelicButtons();
    }

    public void activateAllSoulShiftButtons(boolean activate)
    {
        soulShiftButtonList.forEach(s -> s.runeActivated = activate);
    }

    public void enableAllSoulShiftButtons(boolean enable)
    {
        soulShiftButtonList.forEach(s -> s.buttonEnabled = enable);
    }

    public void makeRelicButtons()
    {
        relicButtonList.add(new RelicBaseButton(new RelicSoulShiftRune(), this));
        relicButtonList.add(new RelicBaseButton(new RelicEggSlicerRune(), this));

        for (int i = 0; i < relicButtonList.size(); i++)
        {
            RelicBaseButton b = relicButtonList.get(i);
            b.tX = defaultWidth + defaultWidth + widthSpacer;
            b.tY = defaultHeight - heightSpacer*i;
            b.buttonEnabled = true;
            logger.info("making button : " + b.name + " at coords ( " + b.tX + ", " + b.tY + " )");
        }
    }

    public void makeSoulShiftButtons()
    {
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftDrawRune(), this));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftEnergyRune(), this));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftBlockRune(), this));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftThornsRune(), this));


        for (int i = 0; i < soulShiftButtonList.size(); i++)
        {
            SoulShiftBaseButton b = soulShiftButtonList.get(i);
            b.tX = defaultWidth;
            b.tY = defaultHeight - heightSpacer*i;
            b.buttonEnabled = true;
            logger.info("making button : " + b.name + " at coords ( " + b.tX + ", " + b.tY + " )");
        }
        soulShiftButtonList.get(0).onUse();
    }

    public void update(){

        if (this.waitThenClose) {
            this.waitToCloseTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitToCloseTimer < 0.0F) {
                this.waitThenClose = false;
                finished();
            }
        }

        soulShiftButtonList.forEach(s -> s.update());
        relicButtonList.forEach(s -> s.update());

        this.confirmButton.update();
        if (this.confirmButton.hb.clicked || CInputActionSet.proceed.isJustPressed() || InputActionSet.confirm.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            this.confirmButton.hb.clicked = false;
            this.waitThenClose = true;
            logger.info("Confirm Button Clicked");
        }

    }

    public void finished()
    {
        logger.info("finished. running Finish script.");

        logger.info("currentScreen is : " + AbstractDungeon.screen + ", previous screen is: " + AbstractDungeon.previousScreen);

        AbstractDungeon.closeCurrentScreen();
    }

    public void close()
    {
        logger.info("closing SoulShopScreen");
        this.confirmButton.hide();
    }

    public void open()
    {
        prep();
    }

    public void prep() {
        logger.info("prepping...");

        this.waitThenClose = false;
        this.waitToCloseTimer = 0.0F;
        AbstractDungeon.isScreenUp = true;
        //AbstractDungeon.previousScreen = AbstractDungeon.screen;
        AbstractDungeon.screen = ReaperEnums.SOULSHOPSCREEN;
        AbstractDungeon.player.hand.stopGlowing();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.overlayMenu.showBlackScreen(0.8F);

        this.confirmButton.hideInstantly();;
        this.confirmButton.show();
        this.confirmButton.isDisabled = false;

        logger.info("isScreenUp: " + AbstractDungeon.isScreenUp + " , currentScreen is : " + AbstractDungeon.screen + ", previous screen is: " + AbstractDungeon.previousScreen);

    }
    public void reopen() {
        logger.info("reopening...");
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);
        this.confirmButton.show();
    }


    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.message, (Settings.WIDTH / 2), Settings.HEIGHT - 180.0F * Settings.scale, Settings.CREAM_COLOR);

        for (int i = 0; i < soulShiftButtonList.size(); i++)
        {
            soulShiftButtonList.get(i).render(sb);
        }
        for( int i = 0; i < relicButtonList.size(); i++)
        {
            relicButtonList.get(i).render(sb);
        }
        this.confirmButton.render(sb);

    }

}
