package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.island.Cell;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;

import java.util.concurrent.ThreadLocalRandom;

// implements Eatable, Moveable, Reproducible
public abstract class Animal extends Organism implements Moveable, Eatable, Reproducible {

    protected boolean alive = true;

    protected int positionX;
    protected int positionY;

    protected double maxSaturation;
    protected double saturation;


    protected int action;

    public static Animal createInstance(Class<? extends Animal> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean canEatPlants();

    public boolean isAlive() {
        return alive;
    }

    public void die(Cell cell) {
        alive = false;
        cell.decAnimal(this);
    }

    public void depletion(Cell cell) {
        double starvation = maxSaturation / 100.0;
        saturation -= starvation;
        if (saturation < 0.0) {
            die(cell);
        }
    }

    public boolean decAction() {
        if (action <= 0) return false;
        action--;
        return true;
    }

    public boolean canAction() {
        return action > 0;
    }

    public void incAction() {
        action++;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int position) {
        positionX = position;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int position) {
        positionY = position;
    }

    public void setMaxSaturation(double maxSaturation) {
        this.maxSaturation = maxSaturation;
    }

    public void setSaturation(double saturation) {
        if (saturation < 0) {
            this.saturation = 0.0;
            return;
        }
        if (saturation > maxSaturation) {
            this.saturation = maxSaturation;
            return;
        }
        this.saturation = saturation;
    }

    @Override
    public void move(Island island) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        Class<? extends Animal> clazz = this.getClass();
        OrganismConfiguration organismConfiguration = island.getorganismConfiguration();
        int maxSteps = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxSteps();

        for (int step = 0; step < maxSteps; step++) {
            int direction = tlr.nextInt(4);
            int newPositionX = positionX;
            int newPositionY = positionY;
            switch (direction) {
                case 0 -> newPositionX++;
                case 1 -> newPositionY++;
                case 2 -> newPositionX--;
                case 3 -> newPositionY--;
                default -> newPositionX++;
            }

            if (island.canMove(this, newPositionX, newPositionY)) {
                island.getCell(positionX, positionY).decAnimal(this);
                island.getCell(newPositionX, newPositionY).incAnimal(this);
                positionX = newPositionX;
                positionY = newPositionY;
            }
        }
    }


    public int getSaturationPercent() {

        return (int) (100.0 * saturation / maxSaturation);


    }

    @Override
    public boolean eat(Island island) {

        return true;
    }

    @Override
    public boolean reproduce(Island island) {
        Animal pairAnimal = island.getPair(this);
        if (pairAnimal == null) {
            return false;
        }
        Class<? extends Animal> clazz = this.getClass();
        Animal childAnimal = Animal.createInstance(clazz);
        childAnimal.setWeight(weight);
        childAnimal.setMaxSaturation(maxSaturation);
        childAnimal.setSaturation(maxSaturation * 0.1);
        childAnimal.setPositionX(positionX);
        childAnimal.setPositionY(positionY);
        Cell cell = island.getCell(positionX, positionY);
        if (!cell.incAnimal(childAnimal)) {
            throw new RuntimeException(clazz.getSimpleName() + " type overflow");
        }
        island.getAnimals().add(childAnimal);
        pairAnimal.decAction();
        return true;
    }
}
