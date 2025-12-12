package ua.net.agsoft.javarush.habitat.simulation;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Moveable;

import java.util.ArrayList;

public class IslandSimulation {

    public void simulate(Island island) {

        // TODO: Начать жить. До момента вымирания всех животных
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (animal != null && animal instanceof Moveable) {
                animal.move(island);
            }
        }


        // Поток отслеживания статистики на острове


        // Поток роста ростительности


        // Поток действий обитателей






    }


}
