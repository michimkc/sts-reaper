package theReaper.souls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.DefaultMod;
import theReaper.util.SoulStrings;

public class HollowSoul extends AbstractSoul {

    public static final Logger logger = LogManager.getLogger(HollowSoul.class.getName());
    // Standard ID/Description
    public static final String soulName = "HollowSoul";
    public static final String SOUL_ID = DefaultMod.makeID(soulName);
    private static final SoulStrings soulString = DefaultMod.SoulStringsMap.get(SOUL_ID);
    public static final String[] DESC = soulString.DESCRIPTIONS;
    public String imgURL = "theReaperResources/images/souls/" + soulName + ".png";
    public Texture img;

    public HollowSoul()
    {
        // load the image
        this.img = ImageMaster.loadImage(imgURL);
        this.ID = SOUL_ID;
        this.name = soulString.NAME;
        this.description = DESC[0];
        this.tX = 0;
    }


    @Override
    public void updateDescription(){
        this.description = DESC[0];
    }

    @Override
    public void UseSoul()
    {

        // what happens when you click the soul
        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA")); // play a Jingle Sound.

        act(new DrawCardAction(AbstractDungeon.player,2));

        super.UseSoul();
    }

    @Override
    public void render(SpriteBatch paramSpriteBatch) {

        paramSpriteBatch.setColor(new Color(1.0f, 1.0f, 1.0f, c.a * 0.8f));
        paramSpriteBatch.draw(this.img, this.tX*Settings.scale, this.tY*Settings.scale, textureWidth/2, textureWidth/2, textureWidth, textureWidth, Settings.scale, Settings.scale, 0, 0, 0, 200, 200, false, false);

        //paramSpriteBatch.draw(this.img,Settings.WIDTH/2, Settings.HEIGHT/2);
        hb.render(paramSpriteBatch);
    }

    @Override
    public AbstractSoul makeCopy()
    {

        return new HollowSoul();
    }

}
