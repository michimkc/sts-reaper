package theReaper.cards;

// Chronometrics

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import theReaper.DefaultMod;
import theReaper.cards.*;
import theReaper.characters.TheDefault;
import theReaper.patches.*;
import theReaper.relics.*;
import theReaper.orbs.*;

public abstract class AbstractCustomCard extends CustomCard {

    public int magicNumber2=0;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseMagicNumber2=0;    // And our base stat - the number in it's base state. It will reset to that by default.
    public int magicNumber2Up = 0;
    public boolean isMagicNumber2Modified=false; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public boolean upgradedMagicNumber2 = false;

    public int damageUp = 0;
    public int blockUp = 0;
    public int magicNumberUp = 0;
    public int newCost = -2; // this allows us to check if the cost has been upgraded.
    public String upgradeDescription = "";

    public AbstractCustomCard(final String id, final int cost,
                               final CardType type, final CardRarity rarity, final CardTarget target) {

        super(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, "theReaperResources/images/cards/" + id.split(":")[1] + ".png", cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type,TheDefault.Enums.COLOR_GRAY, rarity, target);

        this.upgradeDescription = CardCrawlGame.languagePack.getCardStrings(id).UPGRADE_DESCRIPTION;
        isMagicNumber2Modified = false;
    }

    public static void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(damageUp);
            upgradeBlock(blockUp);
            upgradeMagicNumber(magicNumberUp);
            upgradeMagicNumber2(magicNumber2);
            if (newCost != -2) {
                upgradeBaseCost(newCost);
            }

            if (this.upgradeDescription != "" && this.upgradeDescription != null) {
                this.rawDescription = this.upgradeDescription;
            }
            initializeDescription();
        }
    }
    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedMagicNumber2) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            magicNumber2 = baseMagicNumber2; // Show how the number changes, as out of combat, the base number of a card is shown.
            isMagicNumber2Modified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }
    public void upgradeMagicNumber2(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseMagicNumber2 += amount; // Upgrade the number by the amount you provide in your card.
        magicNumber2 = baseMagicNumber2; // Set the number to be equal to the base value.
        upgradedMagicNumber2 = true; // Upgraded = true - which does what the above method does.
    }

}