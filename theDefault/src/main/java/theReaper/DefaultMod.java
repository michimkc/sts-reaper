package theReaper;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.MagicFlower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theReaper.cards.*;
import theReaper.characters.TheDefault;
import theReaper.relics.*;
import theReaper.rune.AbstractSoulShiftRune;
import theReaper.souls.SoulTip;
import theReaper.util.IDCheckDontTouchPls;
import theReaper.util.ReaperStrings;
import theReaper.util.SoulManager;
import theReaper.util.TextureLoader;
import theReaper.variables.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class DefaultMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    // The Reaper:  Soul Manager
    public SoulManager soulManager;
    public static AbstractSoulShiftRune currentShiftRune = null;
    public static SoulTip soulTip;


    // card count
    public static int cardsDrawnThisTurn = 0;

    // marks
    public static int totalMarksConsumedThisCombat = 0;


    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Reaper Mod";
    private static final String AUTHOR = "Rocket"; // And pretty soon - You!
    private static final String DESCRIPTION = "Adds a new character to the game called The Reaper.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theReaperResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theReaperResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theReaperResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theReaperResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theReaperResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theReaperResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theReaperResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theReaperResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theReaperResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theReaperResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theReaperResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theReaperResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theReaperResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theReaperResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theReaperResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theReaperResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theReaperResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    // ReaperStrings Localization
    public static Map<String, ReaperStrings> ReaperStringsMap;


    public DefaultMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("theReaper");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheDefault.Enums.COLOR_GRAY.toString());
        
        BaseMod.addColor(TheDefault.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done adding mod settings");

    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DefaultMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheDefault.Enums.THE_DEFAULT.toString());
        
        BaseMod.addCharacter(new TheDefault("the Default", TheDefault.Enums.THE_DEFAULT),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DEFAULT);
        
        receiveEditPotions();
        logger.info("Added " + TheDefault.Enums.THE_DEFAULT.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // ============== SOUNDS ==================

        logger.info("Adding OLDCHARM sound");
        BaseMod.addAudio("OLDCHARM",getModID() + "Resources/sounds/oldcharmbell.ogg");
        BaseMod.publishAddAudio(CardCrawlGame.sound);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheDefault.Enums.THE_DEFAULT);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new OldCharmRelic(), TheDefault.Enums.COLOR_GRAY);

        BaseMod.addRelicToCustomPool(new AnemoneRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new AzureEyesRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new ClawsRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new CrimsonEyesRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new EggSlicerRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new FadedLocketRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new FangsRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new MandolinRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SilverBeadsRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SmilingCatRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SoulShifterRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SpiritChainsRelic(), TheDefault.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new ThermometerRelic(), TheDefault.Enums.COLOR_GRAY);


        BaseMod.addRelicToCustomPool(new MagicFlower(), TheDefault.Enums.COLOR_GRAY);

        
        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        //UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        BaseMod.addDynamicVariable(new MagicNumber2Variable());
        BaseMod.addDynamicVariable(new BleedNumberVariable());
        BaseMod.addDynamicVariable(new SelfBleedNumberVariable());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new ArcStrike());
        BaseMod.addCard(new Bulwark());
        BaseMod.addCard(new Aegis());
        BaseMod.addCard(new HuntersToll());
        BaseMod.addCard(new Cleanse()); // uncommon skill
        BaseMod.addCard(new ChainStrike());
        BaseMod.addCard(new Contagion());
        BaseMod.addCard(new CrystalRose());
        BaseMod.addCard(new Hemoplague());
        BaseMod.addCard(new CrimsonRush());
        BaseMod.addCard(new Cyclone());
        BaseMod.addCard(new DeathsDance());
        BaseMod.addCard(new DeathRattle());
        BaseMod.addCard(new DeepCuts());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Devour());
        //BaseMod.addCard(new Effigy());
        BaseMod.addCard(new EnduringSpirit());
        //BaseMod.addCard(new FeralInstincts());
        BaseMod.addCard(new FeverDream());
        BaseMod.addCard(new FiendForm());
        BaseMod.addCard(new Fracture());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new Gambit());
        BaseMod.addCard(new Gluttony());
        BaseMod.addCard(new Glowfly());
        BaseMod.addCard(new Guillotine());
        BaseMod.addCard(new Hack());
        BaseMod.addCard(new HastyGrab());
        BaseMod.addCard(new Hemorrhage());
        BaseMod.addCard(new HiddenTalent());
        BaseMod.addCard(new HoningSteel());
        BaseMod.addCard(new HurricaneStrike());
        BaseMod.addCard(new Hyper());
        BaseMod.addCard(new Impatience());
        BaseMod.addCard(new Invigorate());
        BaseMod.addCard(new InsultToInjury());
        BaseMod.addCard(new Lacerate());
        BaseMod.addCard(new LastBreath());
        BaseMod.addCard(new MaimingStrike()); // uncommon attack
        BaseMod.addCard(new Masochism()); //
        BaseMod.addCard(new Moonflower()); //
        //BaseMod.addCard(new MasterBall());
        BaseMod.addCard(new QuickStep());
        BaseMod.addCard(new Reap());
        BaseMod.addCard(new RecklessSwing());
        BaseMod.addCard(new RendingStrike()); // uncommon skill
        BaseMod.addCard(new Regroup()); // uncommon skill
        BaseMod.addCard(new RiteoftheBlade()); // rare power
        BaseMod.addCard(new Papercut()); // uncommon skill
        //BaseMod.addCard(new Payback()); // uncommon skill
        BaseMod.addCard(new Safeguard()); //
        BaseMod.addCard(new SanguineArmour()); // common skill
        BaseMod.addCard(new SerratedKnife()); // common attack
        BaseMod.addCard(new ShadowStep()); // common attack
        BaseMod.addCard(new ShieldWard()); //
        BaseMod.addCard(new Strike()); // common attack
        BaseMod.addCard(new NimbleStrike()); // common attack
        BaseMod.addCard(new SoulBlade()); //
        BaseMod.addCard(new Tantrum());
        BaseMod.addCard(new Transfusion());
        BaseMod.addCard(new Vampirism());

        
        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        UnlockTracker.unlockCard(ArcStrike.ID);
        UnlockTracker.unlockCard(Bulwark.ID);
        UnlockTracker.unlockCard(Aegis.ID);
        UnlockTracker.unlockCard(HuntersToll.ID);
        UnlockTracker.unlockCard(Cleanse.ID);
        UnlockTracker.unlockCard(ChainStrike.ID);
        UnlockTracker.unlockCard(Contagion.ID);
        UnlockTracker.unlockCard(CrimsonRush.ID);
        UnlockTracker.unlockCard(CrystalRose.ID);
        UnlockTracker.unlockCard(Hemoplague.ID);
        UnlockTracker.unlockCard(Cyclone.ID);
        UnlockTracker.unlockCard(DeathsDance.ID);
        UnlockTracker.unlockCard(DeathRattle.ID);
        UnlockTracker.unlockCard(DeepCuts.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Devour.ID);
        //UnlockTracker.unlockCard(Effigy.ID);
        UnlockTracker.unlockCard(EnduringSpirit.ID);
        //UnlockTracker.unlockCard(FeralInstincts.ID);
        UnlockTracker.unlockCard(FeverDream.ID);
        UnlockTracker.unlockCard(FiendForm.ID);
        UnlockTracker.unlockCard(Fracture.ID);
        UnlockTracker.unlockCard(Frenzy.ID);
        UnlockTracker.unlockCard(Gambit.ID);
        UnlockTracker.unlockCard(Gluttony.ID);
        UnlockTracker.unlockCard(Glowfly.ID);
        UnlockTracker.unlockCard(Guillotine.ID);
        UnlockTracker.unlockCard(Hack.ID);
        UnlockTracker.unlockCard(HastyGrab.ID);
        UnlockTracker.unlockCard(Hemorrhage.ID);
        UnlockTracker.unlockCard(HiddenTalent.ID);
        UnlockTracker.unlockCard(HoningSteel.ID);
        UnlockTracker.unlockCard(HurricaneStrike.ID);
        UnlockTracker.unlockCard(Hyper.ID);
        UnlockTracker.unlockCard(Impatience.ID);
        UnlockTracker.unlockCard(Invigorate.ID);
        UnlockTracker.unlockCard(InsultToInjury.ID);
        UnlockTracker.unlockCard(Lacerate.ID);
        UnlockTracker.unlockCard(LastBreath.ID);
        UnlockTracker.unlockCard(MaimingStrike.ID);
        UnlockTracker.unlockCard(Masochism.ID);
        UnlockTracker.unlockCard(Moonflower.ID);
        //UnlockTracker.unlockCard(MasterBall.ID);
        UnlockTracker.unlockCard(QuickStep.ID);
        UnlockTracker.unlockCard(Reap.ID);
        UnlockTracker.unlockCard(RecklessSwing.ID);
        UnlockTracker.unlockCard(Regroup.ID);
        UnlockTracker.unlockCard(RendingStrike.ID);
        UnlockTracker.unlockCard(RiteoftheBlade.ID);
        UnlockTracker.unlockCard(Papercut.ID);
        //UnlockTracker.unlockCard(Payback.ID);
        UnlockTracker.unlockCard(Safeguard.ID);
        UnlockTracker.unlockCard(SanguineArmour.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(ShadowStep.ID);
        UnlockTracker.unlockCard(ShieldWard.ID);
        UnlockTracker.unlockCard(SerratedKnife.ID);
        UnlockTracker.unlockCard(SoulBlade.ID);
        UnlockTracker.unlockCard(NimbleStrike.ID);
        UnlockTracker.unlockCard(Tantrum.ID);
        UnlockTracker.unlockCard(Transfusion.ID);
        UnlockTracker.unlockCard(Vampirism.ID);

        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // Souls / Action Strings
        ReaperStringsMap = ReaperStrings.importReaperStrings();

        // UI / Action Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Ui-Strings.json");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");
        
        logger.info("Done edittting strings");

        // Adding Save Stuff for Souls
        soulManager = new SoulManager();
        BaseMod.addSaveField("theReaperSoulSave", soulManager);

    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
