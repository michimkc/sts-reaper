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


    //public static final String imgURL = "theReaperResources/images/ui/runebutton-off.png";
    public boolean used = false;

    public boolean activated = false;

    public RelicBaseButton(AbstractRelicRune rune, SoulShopScreen scr) {
        super(rune, scr);
        //this.img = ImageMaster.loadImage(imgURL);
        this.ID = rune.getID();
        this.name = rune.getName();
        this.description = rune.getDescription();
        this.tX = 0;
        this.tY = 0;
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


}
