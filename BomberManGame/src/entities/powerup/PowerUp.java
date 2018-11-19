package entities.powerup;

import entities.Entity;

public abstract class PowerUp extends Entity {

    protected boolean isAdded = false;

    public boolean collide(int _x, int _y) {
        return false;
    }
}
