package theReaper.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EggSlicerRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "EggSlicerRelic"; // set this.

    private static final Logger logger = LogManager.getLogger(EggSlicerRelic.class.getName());


    public EggSlicerRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
