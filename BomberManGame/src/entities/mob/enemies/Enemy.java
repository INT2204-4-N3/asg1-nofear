package entities.mob.enemies;

import entities.mob.Mob;
import level.Board;

public abstract class Enemy extends Mob {

    protected int speed, deadTime = 0, speedDelay;

    public Enemy(int x, int y) {
        super(x, y);
    }

    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }

    public boolean canMove(int x, int y) {
        for (int i = 0; i < board.walls.size(); i++) {
            if (board.walls.get(i).collide(x, y)) return false;
        }
        for (int i = 0; i < board.bricks.size(); i++) {
            if (board.bricks.get(i).collide(x, y)) return false;
        }
//        for (int i = 0; i < board.bombsArea.size(); i++) {
//            if (board.bombsArea.get(i).collide(x, y)) return false;
//        }
        return true;
    }
}
