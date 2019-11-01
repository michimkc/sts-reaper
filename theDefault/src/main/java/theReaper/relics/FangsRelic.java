package theReaper.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FangsRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "FangsRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(FangsRelic.class.getName());


    public FangsRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
