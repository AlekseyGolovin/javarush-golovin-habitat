package ua.net.agsoft.javarush.habitat;

import ua.net.agsoft.javarush.habitat.config.Configurator;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.simulation.IslandSimulation;
import ua.net.agsoft.javarush.habitat.util.Util;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        try {
            Path location = Util.getLocation(Main.class);
            Path configPath = location.resolve("settings.json");

            Configurator configurator = new Configurator();
            //Island island = configurator.prepareWorld(configPath);
            Island island = configurator.prepareWorld();

            IslandSimulation islandSimulation = new IslandSimulation();
            islandSimulation.simulate(island);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
