package entities.powerup;

import entities.Entity;

public abstract class PowerUp extends Entity {

    protected boolean isAdded = false;
    protected int duration = 0;
    public int showingTime = 0;

    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }
}
