package theReaper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.buttons.CardSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.actions.CustomGameAction;
import theReaper.patches.SoulSelectEnum;
import theReaper.souls.AbstractSoul;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;


public class SoulSelectScreen {

    public static final Logger logger = LogManager.getLogger(SoulSelectScreen.class.getName());
    public static final String className = "SoulSelectScreen";
    public static final String CLASS_ID = DefaultMod.makeID(className);
    private static final ReaperStrings reaperString = DefaultMod.ReaperStringsMap.get(CLASS_ID);
    public static final String[] DESC = reaperString.DESCRIPTIONS;

    public int numSoulsToSelect;


    public ArrayList<AbstractSoul> selectedSouls = new ArrayList<>();
    public ArrayList<AbstractSoul> unSelectedSouls = new ArrayList<>();

    public String selectionReason;
    public boolean wereSoulsRetrieved = false;
    public boolean canPickZero = true;
    public boolean upTo = false;
    public boolean anyNumber = true;

    public String message = "";
    public CardSelectConfirmButton button = new CardSelectConfirmButton();;


    public boolean waitThenClose = false;
    public float waitToCloseTimer = 0.0F;

    public ArrayList<AbstractSoul> soulList;
    public CustomGameAction sourceAction;

    public static final float SOUL_UNSELECTED_Y_POSITION = 300.0F;
    public static final float SOUL_SELECTED_Y_POSITION = 650.0F;
    public static final float CENTER_SCREEN =Settings.WIDTH/2;

    public SoulSelectScreen()
    {
        logger.info("making soul select screen");
    }

    public void update(){
        updateControllerInput();

        if (this.waitThenClose) {
            this.waitToCloseTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitToCloseTimer < 0.0F) {
                this.waitThenClose = false;
                finished();
            }
        }

        if (Settings.FAST_HAND_CONF && this.numSoulsToSelect == 1 && this.selectedSouls.size() == 1 && !this.canPickZero &&
                !this.waitThenClose) {
            InputHelper.justClickedLeft = false;
            this.waitToCloseTimer = 0.25F;
            this.waitThenClose = true;

            return;
        }

        this.soulList.forEach(s -> s.update());
        this.button.update();
        if (this.button.hb.clicked || CInputActionSet.proceed.isJustPressed() || InputActionSet.confirm.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            this.button.hb.clicked = false;
            if (this.canPickZero && this.selectedSouls.size() == 0) {
                InputHelper.justClickedLeft = false;
                finished();

                return;
            }
            if (this.anyNumber || this.upTo) {
                InputHelper.justClickedLeft = false;
                finished();
                return;
            }
            if (this.selectedSouls.size() == this.numSoulsToSelect) {
                InputHelper.justClickedLeft = false;
                finished();
                return;
            }
        }

    }

    public void finished()
    {
        logger.info("finished. running Finish script.");
        if(soulList.size() > 0) {
            soulList.forEach(s -> s.inSelectionScreen = false);
            soulList.forEach(s -> s.currentSelectScreen = null);
        }
        sourceAction.soulSelectResult(selectedSouls);
        SoulManager.updateSoulIndices();
        AbstractDungeon.closeCurrentScreen();
    }

    public void close()
    {
        logger.info("closing SoulSelectScreen");
        //this.button.hide();
    }

    public void soulClicked(AbstractSoul s)
    {
        logger.info("selected soul with uuid: " + s.uuid);
        if(selectedSouls.size() >= numSoulsToSelect && numSoulsToSelect != 0)
        {
            logger.info("Tried to select a soul, but our selected souls list is already at max specified number of " + numSoulsToSelect);
            return;
        }

        if(selectedSouls.size() > 0) {
            for (int i = 0; i < selectedSouls.size(); i++)
            {
                if (selectedSouls.get(i).uuid == s.uuid)
                {
                    logger.info("soul was in SELECTED group. Removing and putting it into unselected group.");
                    // we clicked on a soul that was selected. so unselect it.
                    unSelectedSouls.add(s);
                    selectedSouls.remove(s);
                    logger.info("Current groups:");
                    logger.info("Selected:");
                    arrayPrint(selectedSouls);
                    logger.info("Unselected:");
                    arrayPrint(unSelectedSouls);
                    display();
                    return;
                }
            }
        }

        if(unSelectedSouls.size() > 0) {

            for (int i = 0; i < unSelectedSouls.size(); i++)
            {
                if(unSelectedSouls.get(i).uuid == s.uuid)
                {
                    logger.info("soul was in UNSELECTED group. Removing and putting it into selected group.");
                    if(numSoulsToSelect == 1)
                    {
                        // we only need to select one
                        s.consumeSoul();
                        return;
                    }
                    selectedSouls.add(s);
                    unSelectedSouls.remove(s);
                    logger.info("Current groups:");
                    logger.info("Selected:");
                    arrayPrint(selectedSouls);
                    logger.info("Unselected:");
                    arrayPrint(unSelectedSouls);
                    display();
                    return;
                }
            }

        }

        logger.info("A soul was selected that wasn't in either the selectedSouls or the unselectedSoul list. this should never happen");
    }

    public void arrayPrint(ArrayList<AbstractSoul> soulList)
    {
        logger.info("Array Size: " + soulList.size());
        if(soulList.size() > 0)
        {
            soulList.forEach(s -> logger.info(  s.uuid + " | "));

        } else
        {
            logger.info("List is empty.");
        }

    }

    public void open(CustomGameAction action, ArrayList<AbstractSoul> sl, String msg, int numberToSelect, boolean anyNumber, boolean canPickZero, boolean upTo)
    {
        unSelectedSouls.clear();
        selectedSouls.clear();
        this.sourceAction = action;
        this.soulList = sl;
        if(sl.size() > 0) {
            soulList.forEach(s -> s.inSelectionScreen = true);
            soulList.forEach(s -> s.currentSelectScreen = this);
            soulList.forEach(s -> unSelectedSouls.add(s));
            display();
        } else {
            // soul list is 0 so nothing to select.
            finished();
        }

        prep();

        this.numSoulsToSelect = numberToSelect;
        this.canPickZero = canPickZero;
        this.anyNumber = anyNumber;
        this.selectionReason = msg;
        this.upTo = upTo;

        if(canPickZero) {
            this.button.isDisabled = true;
            this.button.enable();
        } else {
            this.button.isDisabled = false;
            this.button.disable();
        }
        this.button.hideInstantly();;
        this.button.show();

        updateMessage();
    }

    public void open(CustomGameAction action, ArrayList<AbstractSoul> sl, String msg, int numberToSelect, boolean anyNumber)
    { open(action, sl, msg, numberToSelect, anyNumber, true, true); }

    public void reopen() {
        logger.info("reopening...");
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);
        this.button.show();
    }

    public void prep() {
        logger.info("prepping...");
        this.upTo = false;
        this.canPickZero = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.unhover();
        }
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.actionManager.cleanCardQueue();
        AbstractDungeon.player.releaseCard();
        (AbstractDungeon.getMonsters()).hoveredMonster = null;
        this.waitThenClose = false;
        this.waitToCloseTimer = 0.0F;
        this.selectedSouls.clear();
        this.wereSoulsRetrieved = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = SoulSelectEnum.SOULSELECTSCREEN;
        AbstractDungeon.player.hand.stopGlowing();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.overlayMenu.showBlackScreen(0.5F);

        logger.info("isScreenUp: " + AbstractDungeon.isScreenUp + " , and currentScreen is : " + AbstractDungeon.screen);

    }

    /*
        0 - SELECT 1 SOUL
        1 - SELECT 1 MORE SOUL
        2/3 - "SELECT" <> "MORE SOULS"
        4 - SELECT ANY NUMBER OF SOULS
        5 - SELECT AT LEAST 1 SOUL
        6 - SELECT SOULS

        Preface with msg.
        Example - Consume Souls to Deal Damage. SELECT ANY NUMBER OF SOULS.
     */
    public void updateMessage() {

        this.message = this.selectionReason;

        // need to write if statements to display correct messages.
    }

    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.message, (Settings.WIDTH / 2), Settings.HEIGHT - 180.0F * Settings.scale, Settings.CREAM_COLOR);

        if (!Settings.FAST_HAND_CONF || this.numSoulsToSelect != 1 || this.canPickZero)
        {
            this.button.render(sb);
        }
        this.soulList.forEach(s -> s.render(sb));

        AbstractDungeon.overlayMenu.energyPanel.render(sb);
        //AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);
        //AbstractDungeon.overlayMenu.discardPilePanel.render(sb);
        //AbstractDungeon.overlayMenu.exhaustPanel.render(sb);
    }


    public void display()
    {
        if(this.upTo) {
            this.button.enable();
        } else if (this.selectedSouls.size() == this.numSoulsToSelect)
        {
            this.button.enable();
        } else if (this.selectedSouls.size() > 1 && this.anyNumber && !this.canPickZero)
        {
            this.button.enable();
        } else if (this.selectedSouls.size() != this.numSoulsToSelect && !this.anyNumber)
        {
            this.button.disable();
        } else if (this.anyNumber && this.canPickZero)
        {
            this.button.enable();
        } else if (this.selectedSouls.isEmpty() && !this.canPickZero)
        {
            this.button.enable();
        }

        if(selectedSouls.size() > 0) {

            for (int i = 0; i < selectedSouls.size(); i++)
            {
                selectedSouls.get(i).tX = CENTER_SCREEN + (0.5F*AbstractSoul.textureWidth)
                        + (i*(AbstractSoul.textureWidth + SoulManager.spacerWidth))
                        - (selectedSouls.size()-1)*(AbstractSoul.textureWidth + SoulManager.spacerWidth)/2;
                selectedSouls.get(i).tY = SOUL_SELECTED_Y_POSITION;
            }
        }

        if(unSelectedSouls.size() > 0) {

            float totalWidth = ((unSelectedSouls.size()-1)*SoulManager.spacerWidth + (unSelectedSouls.size()*AbstractSoul.textureWidth)) ;
            float xMod = totalWidth/2;

            for (int i = 0; i < unSelectedSouls.size(); i++)
            {
                unSelectedSouls.get(i).tX = CENTER_SCREEN + (0.5F*AbstractSoul.textureWidth)
                        + (i*(AbstractSoul.textureWidth + SoulManager.spacerWidth))
                        - (unSelectedSouls.size()-1)*(AbstractSoul.textureWidth + SoulManager.spacerWidth)/2;
                unSelectedSouls.get(i).tY = SOUL_UNSELECTED_Y_POSITION;
            }
        }
    }


    private void updateControllerInput() {
        if(!Settings.isControllerMode) {
            return;
        }

        logger.info("CONTROLLER MODE NOT IMPLEMENTED.");



    }
}
