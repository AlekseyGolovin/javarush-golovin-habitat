package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.entity.island.Island;

public interface Reproducible {
    public boolean reproduce(Island island);
}
