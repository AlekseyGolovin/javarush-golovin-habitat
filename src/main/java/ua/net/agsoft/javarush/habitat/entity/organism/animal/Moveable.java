package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.entity.island.Island;

public interface Moveable {


//    public int getPositionX();
//
//    default void setPositionX(int position) {
//        positionX = position;
//    }
//
//    default int getPositionY() {
//        return positionY;
//    }
//
//    default void setPositionY(int position) {
//        positionY = position;
//    }

    public void move(Island island);
}
