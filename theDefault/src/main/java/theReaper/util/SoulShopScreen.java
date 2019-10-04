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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.CustomGameAction;
import theReaper.patches.ReaperEnums;
import theReaper.souls.AbstractSoul;

import java.util.ArrayList;


public class SoulShopScreen {

    public static final Logger logger = LogManager.getLogger(SoulShopScreen.class.getName());
    public static final String className = "SoulShopScreen";
    public static final String CLASS_ID = DefaultMod.makeID(className);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(CLASS_ID);
    //public static final String[] DESC = reaperString.DESCRIPTIONS;

    public String message = "";
    public CardSelectConfirmButton button = new CardSelectConfirmButton();;


    public boolean waitThenClose = false;
    public float waitToCloseTimer = 0.0F;

    public ArrayList<AbstractSoul> soulList;
    public CustomGameAction sourceAction;

    public SoulShopScreen()
    {
        logger.info("making soul shop screen");
    }

    public void update(){

        if (this.waitThenClose) {
            this.waitToCloseTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitToCloseTimer < 0.0F) {
                this.waitThenClose = false;
                finished();
            }
        }

        this.button.update();
        if (this.button.hb.clicked || CInputActionSet.proceed.isJustPressed() || InputActionSet.confirm.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            this.button.hb.clicked = false;

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
        //this.button.hide();
    }

    public void open()
    {

    }

    public void reopen() {
        logger.info("reopening...");
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);
        this.button.show();
    }


    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.message, (Settings.WIDTH / 2), Settings.HEIGHT - 180.0F * Settings.scale, Settings.CREAM_COLOR);

        this.button.render(sb);

    }

}
