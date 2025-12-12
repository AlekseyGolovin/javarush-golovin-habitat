package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;

import java.util.concurrent.ThreadLocalRandom;

// implements Eatable, Moveable, Reproducible
public abstract class Animal extends Organism implements Moveable {

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

    protected abstract boolean canEatPlants();

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public void depletion(){
        double starvation = maxSaturation / 100.0;
        saturation -= starvation;
        if (saturation < 0.0) die();
    }

    public boolean decAction() {
        if (action <= 0) return false;
        action--;
        return true;
    }

    public void incAction() {
        action++;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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


    public void setPositionX(int position) {
        positionX = position;
    }

    public void setPositionY(int position) {
        positionY = position;
    }

    @Override
    public void move(Island island) {
        // Кол-во шагов
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        //System.out.println(this);


        Class<? extends Animal> clazz = this.getClass();
        OrganismConfiguration organismConfiguration = island.getorganismConfiguration();
        int maxSteps = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxSteps();


        //

        for (int step = 0; step < maxSteps; step++) {
            int direction = tlr.nextInt(4);
            //System.out.println("X: "+positionX+" Y: "+positionY);
            //System.out.println("step: "+step+" direction: "+direction);
            int newPositionX = positionX;
            int newPositionY = positionY;
            switch (direction) {
                case 0 -> newPositionX++;
                case 1 -> newPositionY++;
                case 2 -> newPositionX--;
                case 3 -> newPositionY--;
                default -> newPositionX++;
            }

            //System.out.println("X: "+newPositionX+" Y: "+newPositionY);

            if (island.canMove(this, newPositionX, newPositionY)) {
                island.getCell(positionX, positionY).decAnimal(this);
                island.getCell(newPositionX, newPositionY).incAnimal(this);
                positionX = newPositionX;
                positionY = newPositionY;
            } else {
                //System.out.println("Can not move");
            }
        }
    }


}
