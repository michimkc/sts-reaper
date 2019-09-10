package theReaper.souls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;
import theReaper.util.SoulManager;

public abstract class AbstractSoul {
    public String name;
    public String description;
    public String ID;

    protected Color c = Settings.CREAM_COLOR.cpy();
    protected Color shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    protected Texture img = null;
    public float tX;
    public float tY;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static float textureWidth = 150F;
    public int index; // the index of the soul in the player's soul ArrayList

    public Hitbox hb = new Hitbox(textureWidth * Settings.scale, textureWidth * Settings.scale);

    public boolean used = false;

    public abstract void updateDescription();

    public abstract void onUse();

    public abstract AbstractSoul makeCopy();


    public void update() {
        this.hb.update(this.tX*Settings.scale+(textureWidth*0.5f)*Settings.scale, this.tY*Settings.scale+(textureWidth*0.5f)*Settings.scale); // 1.5 is to position per the center of the sprite.
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 15.0F * Settings.scale, this.tY + 5.0F * Settings.scale, this.name, this.description);

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
                UseSoul();
                used = true;
                SoulManager.RemoveSoul(this);
            }
        }
    }

    public abstract void UseSoul();

    public abstract void render(SpriteBatch paramSpriteBatch);

    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }


}
