package theReaper.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theReaper.cards.AbstractCustomCard;

import static theReaper.DefaultMod.makeID;

public class SelfBleedNumberVariable extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("SB"); // B is block. B2 makes it unambiguous
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractCustomCard) card).isSelfBleedNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCustomCard) card).selfBleedNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCustomCard) card).baseSelfBleedNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractCustomCard) card).upgradedSelfBleedNumber;
    }
}