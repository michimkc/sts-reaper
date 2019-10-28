package theReaper.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrimsonEyesRelic extends AbstractSoulRelic {


    // ID, images, text.
    public static final String name = "CrimsonEyesRelic"; // set this.

    public static int bonusMarks = 2;
    private static final Logger logger = LogManager.getLogger(CrimsonEyesRelic.class.getName());


    public CrimsonEyesRelic() {
        super(name);
    }




    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + bonusMarks + DESCRIPTIONS[1];
    }

}
