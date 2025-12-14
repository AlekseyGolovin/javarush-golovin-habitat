package ua.net.agsoft.javarush.habitat.entity.organism.plant;

import ua.net.agsoft.javarush.habitat.entity.organism.Organism;

import java.util.concurrent.ThreadLocalRandom;

public class Plant extends Organism {

    private static int maxCountPerCell = 200;


    public static double getGrowCount(double plantCount) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        return plantCount * 2.0 + tlr.nextInt(maxCountPerCell * 90 / 100);
        //return plantCount + tlr.nextInt(maxCountPerCell * 90 / 100);
    }
}
