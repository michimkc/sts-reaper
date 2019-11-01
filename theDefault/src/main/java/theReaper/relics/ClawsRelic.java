package theReaper.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClawsRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "ClawsRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(ClawsRelic.class.getName());


    public ClawsRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
