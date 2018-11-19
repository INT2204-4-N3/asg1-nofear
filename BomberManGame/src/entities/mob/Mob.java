package entities.mob;

import entities.AnimatedEntity;
import entities.Entity;
import level.Board;

public abstract class Mob extends AnimatedEntity {

    public boolean isAlive = true;
    public boolean isMoving = false;
    public int direction = -1;
    public Board board;

    public Mob(int x, int y, Board board) {
        super.x = x;
        super.y = y;
        this.board = board;
    }

}
