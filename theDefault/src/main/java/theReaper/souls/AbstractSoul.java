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

public abstract class AbstractSoul {
    public String name;
    public String description;
    public String ID;

    public static final Logger logger = LogManager.getLogger(AbstractSoul.class.getName());
    public abstract String getSoulName();

    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 150F;
    public int index; // the index of the soul in the player's soul ArrayList
    public int uuid; // uniqueID assigned by SoulManager

    private static ReaperStrings reaperBaseString;
    public static String[] BaseSoulDescription;
    public static String BaseSoulTipName;

    public Hitbox hb = new Hitbox(textureWidth * Settings.scale, textureWidth * Settings.scale);

    public boolean used = false;
    public boolean inSelectionScreen = false;
    public SoulSelectScreen currentSelectScreen;

    public AbstractSoul(String imgUrl, String soulID, String name, String description) {
        this.img = ImageMaster.loadImage(imgUrl);
        this.ID = soulID;
        this.name = name;
        this.description = description;
        this.tX = 0;
        this.tY = 0;

        reaperBaseString = DefaultMod.ReaperStringsMap.get(DefaultMod.makeID("SoulAction"));
        BaseSoulDescription = reaperBaseString.DESCRIPTIONS;
        BaseSoulTipName = reaperBaseString.NAME;

        LoadSoulShiftRune();
    }

    public void LoadSoulShiftRune()
    {
        if(DefaultMod.currentShiftRune == null)
        {
            DefaultMod.currentShiftRune = new SoulShiftDrawRune(2);
        }
    }

    public abstract void updateDescription();

    public void onUse()
    {
        AbstractDungeon.player.powers.forEach(p -> notifyOnUseSoul(p, this));
        useSoul();
        AbstractDungeon.actionManager.addToBottom(new AbstractSoulOnAfterUseAction(this));
    }

    public abstract AbstractSoul makeCopy();


    public void update() {
        this.hb.move(this.tX, this.tY);
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip((this.tX + 175.0F) * Settings.scale, (this.tY + 300.0F) * Settings.scale, this.name, this.description);
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
                if(inSelectionScreen)
                {
                    // we were pressed but the mode is inSelectionScreen.
                    if(currentSelectScreen == null)
                    {
                        logger.info("currentSelectScreen not set. Cancelling select screen mode for this soul.");
                        inSelectionScreen = false;
                    } else
                    {
                        // selectScreen is set.
                        currentSelectScreen.soulClicked(this);
                    }

                } else {
                    onUse();
                    used = true;
                    SoulManager.RemoveSoul(this);
                }
            }
        }
    }

    public void useSoul() {

        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA")); // play a Jingle Sound.
        SoulManager.useSoul();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCustomCard) {
                ((AbstractCustomCard) c).onSoulUsed(this);
                ((AbstractCustomCard) c).onSoulCountChanged();
            }
        }
    }

    public void consumeSoul()
    {
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
        for (int i = 0; i < 50; i++) {
            AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.tX, this.tY));
        }
        SoulManager.RemoveSoul(this, true);
    }

    public void render(SpriteBatch paramSpriteBatch) {
        paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        paramSpriteBatch.draw(this.img, this.hb.x, this.hb.y, 0, 0, textureWidth, textureWidth, Settings.scale, Settings.scale, 0, 0, 0, 200, 200, false, false);

        //paramSpriteBatch.draw(this.img,Settings.WIDTH/2, Settings.HEIGHT/2);
        hb.render(paramSpriteBatch);
    }

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void notifyOnUseSoul(AbstractPower p, AbstractSoul s)
    {
        if (p instanceof AbstractCustomPower)
        {
            ((AbstractCustomPower)p).onUseSoul(s);
        }
    }

    public static void notifyOnAfterUseSoul(AbstractSoul s)
    {
        AbstractDungeon.player.powers.forEach(p -> afterUseSoulHelper(s,p));
    }

    public static void afterUseSoulHelper(AbstractSoul s, AbstractPower p)
    {
        if (p instanceof AbstractCustomPower)
        {
            ((AbstractCustomPower)p).onAfterUseSoul(s);
        }
    }
}
