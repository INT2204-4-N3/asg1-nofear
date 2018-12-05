package entities.mob;

import entities.AnimatedEntity;
import entities.Entity;
import level.Board;

public abstract class Mob extends AnimatedEntity {

    public boolean isAlive = true;
    public int direction = -1;

    public Mob(int x, int y) {
        super.x = x;
        super.y = y;
    }

}
