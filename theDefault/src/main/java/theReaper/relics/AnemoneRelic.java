package theReaper.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnemoneRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "AnemoneRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(AnemoneRelic.class.getName());

    public static int weakAmount = 1;

    public AnemoneRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
