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


    public static float defaultHeight = Settings.HEIGHT/2.0F + 160.0F * Settings.scale;
    public static float heightSpacer =( SoulShiftBaseButton.textureHeight + 20F )* Settings.scale;
    public static float defaultWidth = 150*Settings.scale;

    public SoulShopScreen()
    {
        logger.info("making soul shop screen");
        soulShiftButtonList = new ArrayList<>();
        makeSoulShiftButtons();
    }

    public void makeSoulShiftButtons()
    {
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftDrawRune()));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftEnergyRune()));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftBlockRune()));
        soulShiftButtonList.add(new SoulShiftBaseButton(new SoulShiftThornsRune()));


        for (int i = 0; i < soulShiftButtonList.size(); i++)
        {
            soulShiftButtonList.get(i).tX = defaultWidth;
            soulShiftButtonList.get(i).tY = defaultHeight - heightSpacer*i;
            soulShiftButtonList.get(i).deActivate();
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
        AbstractDungeon.screen = ReaperEnums.SOULSHOPSCREEN;
        AbstractDungeon.player.hand.stopGlowing();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.overlayMenu.showBlackScreen(0.8F);

        this.confirmButton.hideInstantly();;
        this.confirmButton.show();
        this.confirmButton.isDisabled = false;

        logger.info("isScreenUp: " + AbstractDungeon.isScreenUp + " , and currentScreen is : " + AbstractDungeon.screen);

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
        this.confirmButton.render(sb);

    }

}
