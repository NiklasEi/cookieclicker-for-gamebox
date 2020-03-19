package me.nikl.cookieclicker;

import me.nikl.cookieclicker.boosts.BoostManager;
import me.nikl.cookieclicker.buildings.*;
import me.nikl.cookieclicker.commands.ManipulateCookies;
import me.nikl.cookieclicker.data.Database;
import me.nikl.cookieclicker.data.FileDatabase;
import me.nikl.cookieclicker.data.MySQLDatabase;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.alchemylab.Ambrosia;
import me.nikl.cookieclicker.upgrades.alchemylab.Antimony;
import me.nikl.cookieclicker.upgrades.alchemylab.AquaCrustulae;
import me.nikl.cookieclicker.upgrades.alchemylab.BeigeGoo;
import me.nikl.cookieclicker.upgrades.alchemylab.EssenceOfDough;
import me.nikl.cookieclicker.upgrades.alchemylab.OriginCrucible;
import me.nikl.cookieclicker.upgrades.alchemylab.TheoryOfAtomicFluidity;
import me.nikl.cookieclicker.upgrades.alchemylab.TrueChocolate;
import me.nikl.cookieclicker.upgrades.antimattercondenser.BigBangBake;
import me.nikl.cookieclicker.upgrades.antimattercondenser.LargeMacaronCollider;
import me.nikl.cookieclicker.upgrades.antimattercondenser.Nanocosmics;
import me.nikl.cookieclicker.upgrades.antimattercondenser.ReverseCyclotrons;
import me.nikl.cookieclicker.upgrades.antimattercondenser.SomeOtherSuperTinyFundamentalParticle;
import me.nikl.cookieclicker.upgrades.antimattercondenser.StringTheory;
import me.nikl.cookieclicker.upgrades.antimattercondenser.SugarBosons;
import me.nikl.cookieclicker.upgrades.antimattercondenser.ThePulse;
import me.nikl.cookieclicker.upgrades.bank.AcidProofVaults;
import me.nikl.cookieclicker.upgrades.bank.ChocolateCoins;
import me.nikl.cookieclicker.upgrades.bank.ExponentialInterestRates;
import me.nikl.cookieclicker.upgrades.bank.FinancialZen;
import me.nikl.cookieclicker.upgrades.bank.ScissorResistantCreditCards;
import me.nikl.cookieclicker.upgrades.bank.TallerTellers;
import me.nikl.cookieclicker.upgrades.bank.TheStuffRationale;
import me.nikl.cookieclicker.upgrades.bank.WayOfTheWallet;
import me.nikl.cookieclicker.upgrades.clicking.AdamantiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.EludiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.FantasteelMouse;
import me.nikl.cookieclicker.upgrades.clicking.IronMouse;
import me.nikl.cookieclicker.upgrades.clicking.NevercrackMouse;
import me.nikl.cookieclicker.upgrades.clicking.PlasticMouse;
import me.nikl.cookieclicker.upgrades.clicking.TitaniumMouse;
import me.nikl.cookieclicker.upgrades.clicking.UnobtainiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.WishalloyMouse;
import me.nikl.cookieclicker.upgrades.curser.Ambidextrous;
import me.nikl.cookieclicker.upgrades.curser.BillionFingers;
import me.nikl.cookieclicker.upgrades.curser.CarpalTunnelPreventionCream;
import me.nikl.cookieclicker.upgrades.curser.MillionFingers;
import me.nikl.cookieclicker.upgrades.curser.OctillionFingers;
import me.nikl.cookieclicker.upgrades.curser.QuadrillionFingers;
import me.nikl.cookieclicker.upgrades.curser.QuintillionFingers;
import me.nikl.cookieclicker.upgrades.curser.ReinforcedIndexFinger;
import me.nikl.cookieclicker.upgrades.curser.SeptillionFingers;
import me.nikl.cookieclicker.upgrades.curser.SextillionFingers;
import me.nikl.cookieclicker.upgrades.curser.ThousandFingers;
import me.nikl.cookieclicker.upgrades.curser.TrillionFingers;
import me.nikl.cookieclicker.upgrades.factory.ChildLabor;
import me.nikl.cookieclicker.upgrades.factory.CyborgWorkforce;
import me.nikl.cookieclicker.upgrades.factory.DeepBakeProcess;
import me.nikl.cookieclicker.upgrades.factory.HourDays;
import me.nikl.cookieclicker.upgrades.factory.RadiumReactors;
import me.nikl.cookieclicker.upgrades.factory.Recombobulators;
import me.nikl.cookieclicker.upgrades.factory.SturdierConveyorBelts;
import me.nikl.cookieclicker.upgrades.factory.Sweatshop;
import me.nikl.cookieclicker.upgrades.farm.CheapHoes;
import me.nikl.cookieclicker.upgrades.farm.CookieTrees;
import me.nikl.cookieclicker.upgrades.farm.Fertilizer;
import me.nikl.cookieclicker.upgrades.farm.FudgeFungus;
import me.nikl.cookieclicker.upgrades.farm.GeneticallyModifiedCookies;
import me.nikl.cookieclicker.upgrades.farm.GingerbreadScarecrows;
import me.nikl.cookieclicker.upgrades.farm.PulsarSprinklers;
import me.nikl.cookieclicker.upgrades.farm.WheatTriffids;
import me.nikl.cookieclicker.upgrades.grandma.AgingAgents;
import me.nikl.cookieclicker.upgrades.grandma.DoubleThickGlasses;
import me.nikl.cookieclicker.upgrades.grandma.ForwardsFromGrandma;
import me.nikl.cookieclicker.upgrades.grandma.LubricatedDentures;
import me.nikl.cookieclicker.upgrades.grandma.PruneJuice;
import me.nikl.cookieclicker.upgrades.grandma.SteelPlatedRollingPins;
import me.nikl.cookieclicker.upgrades.grandma.TheUnbridling;
import me.nikl.cookieclicker.upgrades.grandma.XtremeWalkers;
import me.nikl.cookieclicker.upgrades.mine.CanolaOilWells;
import me.nikl.cookieclicker.upgrades.mine.Coreforge;
import me.nikl.cookieclicker.upgrades.mine.HBombMining;
import me.nikl.cookieclicker.upgrades.mine.Megadrill;
import me.nikl.cookieclicker.upgrades.mine.Planetsplitters;
import me.nikl.cookieclicker.upgrades.mine.SugarGas;
import me.nikl.cookieclicker.upgrades.mine.Ultimadrill;
import me.nikl.cookieclicker.upgrades.mine.Ultradrill;
import me.nikl.cookieclicker.upgrades.portal.AncientTablet;
import me.nikl.cookieclicker.upgrades.portal.BraneTransplant;
import me.nikl.cookieclicker.upgrades.portal.DeitySizedPortals;
import me.nikl.cookieclicker.upgrades.portal.EndOfTimesBackUpPlan;
import me.nikl.cookieclicker.upgrades.portal.InsaneOatlingWorkers;
import me.nikl.cookieclicker.upgrades.portal.MaddeningChants;
import me.nikl.cookieclicker.upgrades.portal.SanityDance;
import me.nikl.cookieclicker.upgrades.portal.SoulBond;
import me.nikl.cookieclicker.upgrades.prism.ChocolateLight;
import me.nikl.cookieclicker.upgrades.prism.GemPolish;
import me.nikl.cookieclicker.upgrades.prism.GlowInTheDark;
import me.nikl.cookieclicker.upgrades.prism.Grainbow;
import me.nikl.cookieclicker.upgrades.prism.LuxSanctorum;
import me.nikl.cookieclicker.upgrades.prism.NinethColor;
import me.nikl.cookieclicker.upgrades.prism.PureCosmicLight;
import me.nikl.cookieclicker.upgrades.prism.ReverseShadows;
import me.nikl.cookieclicker.upgrades.shipment.ChocolateMonoliths;
import me.nikl.cookieclicker.upgrades.shipment.DysonSphere;
import me.nikl.cookieclicker.upgrades.shipment.FrequentFlyer;
import me.nikl.cookieclicker.upgrades.shipment.GenerationShip;
import me.nikl.cookieclicker.upgrades.shipment.TheFinalFrontier;
import me.nikl.cookieclicker.upgrades.shipment.VanillaNebulae;
import me.nikl.cookieclicker.upgrades.shipment.WarpDrive;
import me.nikl.cookieclicker.upgrades.shipment.Wormholes;
import me.nikl.cookieclicker.upgrades.temple.CreationMyth;
import me.nikl.cookieclicker.upgrades.temple.DeliciousBlessing;
import me.nikl.cookieclicker.upgrades.temple.EnlargedPantheon;
import me.nikl.cookieclicker.upgrades.temple.GoldenIdols;
import me.nikl.cookieclicker.upgrades.temple.GreatBakerInTheSky;
import me.nikl.cookieclicker.upgrades.temple.Sacrifices;
import me.nikl.cookieclicker.upgrades.temple.SunFestival;
import me.nikl.cookieclicker.upgrades.temple.Theocracy;
import me.nikl.cookieclicker.upgrades.timemachine.CausalityEnforcer;
import me.nikl.cookieclicker.upgrades.timemachine.CookietopianMomentsOfMaybe;
import me.nikl.cookieclicker.upgrades.timemachine.FarFutureEnactment;
import me.nikl.cookieclicker.upgrades.timemachine.FluxCapacitors;
import me.nikl.cookieclicker.upgrades.timemachine.GreatLoopHypothesis;
import me.nikl.cookieclicker.upgrades.timemachine.QuantumConundrum;
import me.nikl.cookieclicker.upgrades.timemachine.TimeParadoxResolver;
import me.nikl.cookieclicker.upgrades.timemachine.YestermorrowComparators;
import me.nikl.cookieclicker.upgrades.wizardtower.AncientGrimoires;
import me.nikl.cookieclicker.upgrades.wizardtower.BeardlierBeards;
import me.nikl.cookieclicker.upgrades.wizardtower.Cookiemancy;
import me.nikl.cookieclicker.upgrades.wizardtower.DarkFormulas;
import me.nikl.cookieclicker.upgrades.wizardtower.KitchenCurses;
import me.nikl.cookieclicker.upgrades.wizardtower.PointierHats;
import me.nikl.cookieclicker.upgrades.wizardtower.RabbitTrick;
import me.nikl.cookieclicker.upgrades.wizardtower.SchoolOfSorcery;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.game.Game;
import me.nikl.gamebox.game.GameSettings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Niklas Eicker
 *
 * Main class of the GameBox game Cookie Clicker
 */
public class CookieClicker extends Game {
    private Database database;
    private Map<Buildings, Building> buildings = new HashMap<>();
    private Map<Integer, Buildings> buildingsPositions = new HashMap<>();
    private Map<Integer, Upgrade> upgrades = new HashMap<>();
    private BoostManager boostManager;

    public CookieClicker(GameBox gameBox) {
        super(gameBox, GameBox.MODULE_COOKIECLICKER);
    }

    @Override
    public void onDisable() {
        ((CCGameManager) gameManager).onShutDown();
        database.onShutDown();
    }

    @Override
    public void init() {
        loadBuildings();
        loadUpgrades();
        if (GameBoxSettings.useMysql) {
            this.database = new MySQLDatabase(this);
        } else {
            this.database = new FileDatabase(this);
        }
    }

    @Override
    protected void finish() {
        this.boostManager = new BoostManager(this);
        gameBox.getCommands().getCommandReplacements().addReplacement("cc_game_ids", String.join(":", gameManager.getGameRules().keySet()));
        gameBox.getCommands().getCommandReplacements().addReplacement("cc_subcommands", String.join("|", getModule().getSubCommands()));
        gameBox.getCommands().registerCommand(new ManipulateCookies(gameBox, this));
    }

    @Override
    public void loadSettings() {
        gameSettings.setGameType(GameSettings.GameType.SINGLE_PLAYER);
        gameSettings.setGameGuiSize(54);
        gameSettings.setHandleClicksOnHotbar(false);
    }

    @Override
    public void loadLanguage() {
        this.gameLang = new CCLanguage(this);
    }

    @Override
    public void loadGameManager() {
        this.gameManager = new CCGameManager(this);
    }

    public Database getDatabase() {
        return this.database;
    }


    private void loadBuildings() {
        // add all buildings and register them with their slot
        buildings.put(Buildings.CURSOR, new Cursor(this, 2, Buildings.CURSOR));
        buildingsPositions.put(2, Buildings.CURSOR);
        buildings.put(Buildings.GRANDMA, new Grandma(this, 3, Buildings.GRANDMA));
        buildingsPositions.put(3, Buildings.GRANDMA);
        buildings.put(Buildings.FARM, new Farm(this, 4, Buildings.FARM));
        buildingsPositions.put(4, Buildings.FARM);
        buildings.put(Buildings.MINE, new Mine(this, 5, Buildings.MINE));
        buildingsPositions.put(5, Buildings.MINE);
        buildings.put(Buildings.FACTORY, new Factory(this, 6, Buildings.FACTORY));
        buildingsPositions.put(6, Buildings.FACTORY);
        buildings.put(Buildings.BANK, new Bank(this, 7, Buildings.BANK));
        buildingsPositions.put(7, Buildings.BANK);
        buildings.put(Buildings.TEMPLE, new Temple(this, 8, Buildings.TEMPLE));
        buildingsPositions.put(8, Buildings.TEMPLE);
        buildings.put(Buildings.WIZARD_TOWER, new WizardTower(this, 11, Buildings.WIZARD_TOWER));
        buildingsPositions.put(11, Buildings.WIZARD_TOWER);
        buildings.put(Buildings.SHIPMENT, new Shipment(this, 12, Buildings.SHIPMENT));
        buildingsPositions.put(12, Buildings.SHIPMENT);
        buildings.put(Buildings.ALCHEMY_LAB, new AlchemyLab(this, 13, Buildings.ALCHEMY_LAB));
        buildingsPositions.put(13, Buildings.ALCHEMY_LAB);
        buildings.put(Buildings.PORTAL, new Portal(this, 14, Buildings.PORTAL));
        buildingsPositions.put(14, Buildings.PORTAL);
        buildings.put(Buildings.TIME_MACHINE, new TimeMachine(this, 15, Buildings.TIME_MACHINE));
        buildingsPositions.put(15, Buildings.TIME_MACHINE);
        buildings.put(Buildings.ANTIMATTER_CONDENSER, new AntimatterCondenser(this, 16, Buildings.ANTIMATTER_CONDENSER));
        buildingsPositions.put(16, Buildings.ANTIMATTER_CONDENSER);
        buildings.put(Buildings.PRISM, new Prism(this, 17, Buildings.PRISM));
        buildingsPositions.put(17, Buildings.PRISM);
    }

    private void loadUpgrades() {
        Set<Upgrade> futureUpgradesTemp = new HashSet<>();
        // clicking
        futureUpgradesTemp.add(new PlasticMouse(this));
        futureUpgradesTemp.add(new IronMouse(this));
        futureUpgradesTemp.add(new TitaniumMouse(this));
        futureUpgradesTemp.add(new AdamantiumMouse(this));
        futureUpgradesTemp.add(new UnobtainiumMouse(this));
        futureUpgradesTemp.add(new EludiumMouse(this));
        futureUpgradesTemp.add(new WishalloyMouse(this));
        futureUpgradesTemp.add(new FantasteelMouse(this));
        futureUpgradesTemp.add(new NevercrackMouse(this));

        // Cursor
        futureUpgradesTemp.add(new CarpalTunnelPreventionCream(this));
        futureUpgradesTemp.add(new ReinforcedIndexFinger(this));
        futureUpgradesTemp.add(new Ambidextrous(this));
        futureUpgradesTemp.add(new ThousandFingers(this));
        futureUpgradesTemp.add(new MillionFingers(this));
        futureUpgradesTemp.add(new BillionFingers(this));
        futureUpgradesTemp.add(new TrillionFingers(this));
        futureUpgradesTemp.add(new QuadrillionFingers(this));
        futureUpgradesTemp.add(new QuintillionFingers(this));
        futureUpgradesTemp.add(new SextillionFingers(this));
        futureUpgradesTemp.add(new SeptillionFingers(this));
        futureUpgradesTemp.add(new OctillionFingers(this));

        // GRANDMA
        futureUpgradesTemp.add(new ForwardsFromGrandma(this));
        futureUpgradesTemp.add(new SteelPlatedRollingPins(this));
        futureUpgradesTemp.add(new LubricatedDentures(this));
        futureUpgradesTemp.add(new PruneJuice(this));
        futureUpgradesTemp.add(new DoubleThickGlasses(this));
        futureUpgradesTemp.add(new AgingAgents(this));
        futureUpgradesTemp.add(new XtremeWalkers(this));
        futureUpgradesTemp.add(new TheUnbridling(this));

        // Farm
        futureUpgradesTemp.add(new CheapHoes(this));
        futureUpgradesTemp.add(new Fertilizer(this));
        futureUpgradesTemp.add(new CookieTrees(this));
        futureUpgradesTemp.add(new GeneticallyModifiedCookies(this));
        futureUpgradesTemp.add(new GingerbreadScarecrows(this));
        futureUpgradesTemp.add(new PulsarSprinklers(this));
        futureUpgradesTemp.add(new FudgeFungus(this));
        futureUpgradesTemp.add(new WheatTriffids(this));

        // Mine
        futureUpgradesTemp.add(new SugarGas(this));
        futureUpgradesTemp.add(new Megadrill(this));
        futureUpgradesTemp.add(new Ultradrill(this));
        futureUpgradesTemp.add(new Ultimadrill(this));
        futureUpgradesTemp.add(new HBombMining(this));
        futureUpgradesTemp.add(new Coreforge(this));
        futureUpgradesTemp.add(new Planetsplitters(this));
        futureUpgradesTemp.add(new CanolaOilWells(this));

        // Factory
        futureUpgradesTemp.add(new SturdierConveyorBelts(this));
        futureUpgradesTemp.add(new ChildLabor(this));
        futureUpgradesTemp.add(new Sweatshop(this));
        futureUpgradesTemp.add(new RadiumReactors(this));
        futureUpgradesTemp.add(new Recombobulators(this));
        futureUpgradesTemp.add(new DeepBakeProcess(this));
        futureUpgradesTemp.add(new CyborgWorkforce(this));
        futureUpgradesTemp.add(new HourDays(this));

        // Bank
        futureUpgradesTemp.add(new TallerTellers(this));
        futureUpgradesTemp.add(new ScissorResistantCreditCards(this));
        futureUpgradesTemp.add(new AcidProofVaults(this));
        futureUpgradesTemp.add(new ChocolateCoins(this));
        futureUpgradesTemp.add(new ExponentialInterestRates(this));
        futureUpgradesTemp.add(new FinancialZen(this));
        futureUpgradesTemp.add(new WayOfTheWallet(this));
        futureUpgradesTemp.add(new TheStuffRationale(this));

        // Temple
        futureUpgradesTemp.add(new GoldenIdols(this));
        futureUpgradesTemp.add(new Sacrifices(this));
        futureUpgradesTemp.add(new DeliciousBlessing(this));
        futureUpgradesTemp.add(new SunFestival(this));
        futureUpgradesTemp.add(new EnlargedPantheon(this));
        futureUpgradesTemp.add(new GreatBakerInTheSky(this));
        futureUpgradesTemp.add(new CreationMyth(this));
        futureUpgradesTemp.add(new Theocracy(this));

        // Wizard Tower
        futureUpgradesTemp.add(new PointierHats(this));
        futureUpgradesTemp.add(new BeardlierBeards(this));
        futureUpgradesTemp.add(new AncientGrimoires(this));
        futureUpgradesTemp.add(new KitchenCurses(this));
        futureUpgradesTemp.add(new SchoolOfSorcery(this));
        futureUpgradesTemp.add(new DarkFormulas(this));
        futureUpgradesTemp.add(new Cookiemancy(this));
        futureUpgradesTemp.add(new RabbitTrick(this));

        // Shipment
        futureUpgradesTemp.add(new VanillaNebulae(this));
        futureUpgradesTemp.add(new Wormholes(this));
        futureUpgradesTemp.add(new FrequentFlyer(this));
        futureUpgradesTemp.add(new WarpDrive(this));
        futureUpgradesTemp.add(new ChocolateMonoliths(this));
        futureUpgradesTemp.add(new GenerationShip(this));
        futureUpgradesTemp.add(new DysonSphere(this));
        futureUpgradesTemp.add(new TheFinalFrontier(this));

        // Alchemy Lab
        futureUpgradesTemp.add(new Antimony(this));
        futureUpgradesTemp.add(new EssenceOfDough(this));
        futureUpgradesTemp.add(new TrueChocolate(this));
        futureUpgradesTemp.add(new Ambrosia(this));
        futureUpgradesTemp.add(new AquaCrustulae(this));
        futureUpgradesTemp.add(new OriginCrucible(this));
        futureUpgradesTemp.add(new TheoryOfAtomicFluidity(this));
        futureUpgradesTemp.add(new BeigeGoo(this));

        // Portal
        futureUpgradesTemp.add(new AncientTablet(this));
        futureUpgradesTemp.add(new InsaneOatlingWorkers(this));
        futureUpgradesTemp.add(new SoulBond(this));
        futureUpgradesTemp.add(new SanityDance(this));
        futureUpgradesTemp.add(new BraneTransplant(this));
        futureUpgradesTemp.add(new DeitySizedPortals(this));
        futureUpgradesTemp.add(new EndOfTimesBackUpPlan(this));
        futureUpgradesTemp.add(new MaddeningChants(this));

        // Time Machine
        futureUpgradesTemp.add(new FluxCapacitors(this));
        futureUpgradesTemp.add(new TimeParadoxResolver(this));
        futureUpgradesTemp.add(new QuantumConundrum(this));
        futureUpgradesTemp.add(new CausalityEnforcer(this));
        futureUpgradesTemp.add(new YestermorrowComparators(this));
        futureUpgradesTemp.add(new FarFutureEnactment(this));
        futureUpgradesTemp.add(new GreatLoopHypothesis(this));
        futureUpgradesTemp.add(new CookietopianMomentsOfMaybe(this));

        // Antimatter Condenser
        futureUpgradesTemp.add(new SugarBosons(this));
        futureUpgradesTemp.add(new StringTheory(this));
        futureUpgradesTemp.add(new LargeMacaronCollider(this));
        futureUpgradesTemp.add(new BigBangBake(this));
        futureUpgradesTemp.add(new ReverseCyclotrons(this));
        futureUpgradesTemp.add(new Nanocosmics(this));
        futureUpgradesTemp.add(new ThePulse(this));
        futureUpgradesTemp.add(new SomeOtherSuperTinyFundamentalParticle(this));

        // Prism Condenser
        futureUpgradesTemp.add(new GemPolish(this));
        futureUpgradesTemp.add(new NinethColor(this));
        futureUpgradesTemp.add(new ChocolateLight(this));
        futureUpgradesTemp.add(new Grainbow(this));
        futureUpgradesTemp.add(new PureCosmicLight(this));
        futureUpgradesTemp.add(new GlowInTheDark(this));
        futureUpgradesTemp.add(new LuxSanctorum(this));
        futureUpgradesTemp.add(new ReverseShadows(this));


        // sort upgrades in map with ids as key (fast lookup for loading of old game)
        Upgrade upgrade;
        Iterator<Upgrade> iterator = futureUpgradesTemp.iterator();
        while (iterator.hasNext()) {
            upgrade = iterator.next();
            upgrades.put(upgrade.getId(), upgrade);
        }
    }

    public Map<Buildings, Building> getBuildings() {
        return this.buildings;
    }

    public Map<Integer, Buildings> getBuildingsPositions() {
        return this.buildingsPositions;
    }

    public Upgrade getUpgrade(int id) {
        return upgrades.get(id);
    }

    public Map<Integer, Upgrade> getUpgrades() {
        return this.upgrades;
    }

    public boolean isBuildingSlot(int rawSlot) {
        return buildingsPositions.containsKey(rawSlot);
    }

    public Building getBuilding(int rawSlot) {
        return buildings.get(buildingsPositions.get(rawSlot));
    }

    public Building getBuilding(Buildings type) {
        return buildings.get(type);
    }

    public Set<Integer> getUpgradeIDs() {
        return new HashSet<>(upgrades.keySet());
    }

    public void prepareGame(UUID gameUuid) {
        for (Building building : buildings.values()) {
            building.prepareGame(gameUuid);
        }
    }

    public void removeGame(UUID gameUuid) {
        for (Building building : buildings.values()) {
            building.removeGame(gameUuid);
        }
    }

    public BoostManager getBoostManager() {
        return boostManager;
    }
}
