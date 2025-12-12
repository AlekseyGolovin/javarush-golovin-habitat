package ua.net.agsoft.javarush.habitat.config;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import java.nio.file.Path;

public class Configurator {

    Island island;
    OrganismConfiguration organismConfiguration;

    public Island prepareWorld(Path configPath) {
        FileConfigurator fileConfigurator = new FileConfigurator(configPath);
        organismConfiguration = fileConfigurator.configureOrganism();
        island = fileConfigurator.configureIsland();
        return populate();
    }

    public Island prepareWorld() {
        DefaultConfigurator defaultConfigurator = new DefaultConfigurator();
        organismConfiguration = defaultConfigurator.configureOrganism();
        island = defaultConfigurator.configureIsland();
        return populate();
    }

    private Island populate() {
        island.populate(organismConfiguration);
        return island;
    }


}
