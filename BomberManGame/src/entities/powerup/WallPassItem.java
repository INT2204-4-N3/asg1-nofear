package entities.powerup;

import entities.Entity;
import graphics.Screen;

public class WallPassItem extends PowerUp {


    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {

    }
}
